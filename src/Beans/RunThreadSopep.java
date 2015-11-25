package Beans;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

public class RunThreadSopep implements Runnable
{
	private String folderName = "";
	private String email =  "";
	private String taskName = "";
	
	public void setFolder(String folderName, String taskName, String email)
	{
		this.folderName = folderName;
		this.email = email;
		this.taskName = taskName;
	}
	
	public void run()
	{
		/**
		 * 1. split fasta file
		 */
		SplitSeqSopep split = new SplitSeqSopep();
		try
		{
			split.splitSeq(folderName);
		}
		catch(Exception e)
		{
			File log = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/log.txt");
			try
			{
				FileWriter fw = new FileWriter(log, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("split file===============\n");
				bw.write(e.getMessage() + "\n");
				bw.flush();
				bw.close();
				fw.close();
			}
			catch(Exception estdio)
			{
				estdio.printStackTrace();
			}
		}
		
		/**
		 * 2. get sequence and run model
		 */
		try
		{
			File dir = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/fasta/");
			File files[] = dir.listFiles();
			
			File res = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/result.txt");
		    FileWriter fwres = new FileWriter(res, true);
		    BufferedWriter bwres = new BufferedWriter(fwres);
		    bwres.write("Email to: " + email + "\n");
		    bwres.write("Task Name: " + taskName + "\n");
		    bwres.write("Task ID: " + folderName + "\n");
		    bwres.write("column format: sequence fasta header===expression level classification matrix (High, Low, Medium)===classification decision===expression level value (mg/L)\n");
		    bwres.flush();
			
			for(int i = 0 ; i < files.length; i++)
			{
				if(files[i].getName().endsWith(".fasta"))
				{
					FileReader fr = new FileReader(files[i]);
					BufferedReader br = new BufferedReader(fr);
					String str = br.readLine();
					String inputSequence = "";
					String header = "";
					
					while(str!=null)
					{
						str = str.trim();
						if(!str.startsWith(">"))
							inputSequence = inputSequence + str;
						else
						{
							header = str;
						}
						str = br.readLine();
					}
					br.close();
					fr.close();
					
					
					//first run the SeqRate	then get SeqRate result
					double L1b = 0.0;
				    	double L4b = 0.0;
				    	String line = "";
					try
					{
						Process p1 = Runtime.getRuntime().exec("java -jar /var/lib/tomcat7/webapps/periscope/foldrate/SVMFoldRate.jar " + files[i].getAbsolutePath() +  " " + files[i].getName() + " multi-state /var/lib/tomcat7/webapps/periscope/foldrate/pspro1.1/bin/predict_ss_sa_cm.sh /var/lib/tomcat7/webapps/periscope/foldrate/test /var/lib/tomcat7/webapps/periscope/foldrate/otherTools/ svm_classify /var/lib/tomcat7/webapps/periscope/users/"+folderName + "/fasta/" + files[i].getName().split("\\.")[0]);
						
						System.out.println("java -jar /var/lib/tomcat7/webapps/periscope/foldrate/SVMFoldRate.jar " + files[i].getAbsolutePath() +  " " + files[i].getName() + " multi-state /var/lib/tomcat7/webapps/periscope/foldrate/pspro1.1/bin/predict_ss_sa_cm.sh /var/lib/tomcat7/webapps/periscope/foldrate/test /var/lib/tomcat7/webapps/periscope/foldrate/otherTools/ svm_classify /var/lib/tomcat7/webapps/periscope/users/"+folderName + "/fasta/" + files[i].getName().split("\\.")[0]);
						p1.waitFor();
						
						File seqR = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/fasta/" + files[i].getName().split("\\.")[0] + "/predOut.txt");
					    	FileReader fs = new FileReader(seqR);
					    	BufferedReader bs = new BufferedReader(fs);
					    	line = bs.readLine();
					    	
					    	while(line!=null)
					    	{
					    		line = line.trim();
					    		if(line.startsWith("predicted folding rate in log based 10 value:"))
					    		{
					    			L1b = Double.parseDouble(line.split(":")[1].trim());
					    		}
					    		if(line.startsWith("predicted folding rate in natural log value:"))
					    		{
					    			L4b = Double.parseDouble(line.split(":")[1].trim());
					    		}
					    		line = bs.readLine();
					    	}
					    	bs.close();
					    	fs.close();
					}
					catch(Exception e)
					{
						File log = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/log.txt");
						FileWriter fw = new FileWriter(log, true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write("SeqRate===============\n");
						bw.write(e.getMessage() + "\n");
						bw.flush();
						bw.close();
						fw.close();
					}
				    	double C4 = L1b;
				    	
				    	System.out.println(C4 + "   " + L1b + "   " + L4b);
				    	
				    	
					
				    	//run the tmhmm then get tmhmm result
				    	double L3b = 0.0;
				    	try
				    	{
				    		Process p = Runtime.getRuntime().exec("/var/lib/tomcat7/webapps/periscope/tmhmm/tmhmm-2.0c/bin/tmhmm " + files[i].getAbsolutePath());
				    		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				    		String msg = "";
				    		
				    		File out = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/fasta/" + files[i].getName().split("\\.")[0] + "/tmhmm.txt");
				    		FileWriter fw = new FileWriter(out);
				    		BufferedWriter bw = new BufferedWriter(fw);
				    		while ( (msg=bufferedReader.readLine()) != null) 
				    		{
				    			//System.out.println(msg);
				    			bw.write(msg + "\n");
				    			bw.flush();
				    		}	    		
				    		bw.close();
				    		fw.close();
				    		p.waitFor();

				    		File tmhmm = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/fasta/" + files[i].getName().split("\\.")[0] + "/tmhmm.txt");
					    	FileReader ft = new FileReader(tmhmm);
					    	BufferedReader bt = new BufferedReader(ft);
					    	line = bt.readLine();
					    	
					    	while(line!=null)
					    	{
					    		line = line.trim();
					    		if(line.contains("Number of predicted TMHs:"))
					    		{
					    			L3b = Double.parseDouble(line.split(":")[1].trim());
					    			break;
					    		}
					    		line = bt.readLine();
					    	}
					    	bt.close();
					    	ft.close();
				    	}
				    	catch (Exception e)
				    	{
				    		File log = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/log.txt");
						FileWriter fw = new FileWriter(log, true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write("tmhmm===============\n");
						bw.write(e.getMessage() + "\n");
						bw.flush();
						bw.close();
						fw.close();
				    	}

				    	
				    	//get result from ExPASy
					double M6b = 0.0;
					String url = "http://web.expasy.org/cgi-bin/compute_pi/pi_tool?protein=" +inputSequence;
					URL U = new URL(url);
					HttpURLConnection connection = (HttpURLConnection)U.openConnection();
					connection.connect();
							
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					while ((line = in.readLine())!= null)
					{
						line = line.trim();
						if(line.startsWith("Theoretical pI/Mw:"))
						{
							M6b = Double.parseDouble(line.split(":")[1].substring(0,line.split(":")[1].indexOf("/")).trim());
							break;
						}
					}
					in.close();
							
					System.out.println(L1b + "  " + L4b + "  " + C4 + "  " + L3b + "  " + M6b);

					
					/**
					 * call Catherine's code
					 */
					// Assign mean and SD of each feature wrt models
					// Classification model
					double C1mean = 0.12675, C1SD = 0.036569;
					double C2mean = 0.040073, C2SD = 0.017005;
					double C3mean = 3.969388, C3SD = 2.043113;
					double C4mean = 0.724831, C4SD = 0.634092;
					double C5mean = 0.346939, C5SD = 0.593818;
					double C6mean = 0.469388, C6SD = 0.720883;
					double C7mean = 0.908163, C7SD = 1.103759;
					// Regression High model
					double H1mean = 0.6875, H1SD = 0.873212;
					double H2mean = 2.0, H2SD = 1.67332;
					double H3mean = 0.088922, H3SD = 0.032108;
					// Regression Low model
					double L1mean = 0.025799, L1SD = 0.02637;
					double L2mean = 0.640834, L2SD = 0.293738;
					double L3mean = 1.583333, L3SD = 4.200587;
					double L4mean = 0.052095, L4SD = 0.056179;
					// Regression Medium model
					double M1mean = 0.672414, M1SD = 1.032591;
					double M2mean = 0.12069, M2SD = 0.37825;
					double M3mean = 1.62069, M3SD = 1.281824;
					double M4mean = -0.001092672, M4SD = 0.001089688;
					double M5mean = 0.817358, M5SD = 0.326342;
					double M6mean = 7.326316, M6SD = 2.831285;		
			
					//  === CLASSIFICATION ===
					FastVector	atts;
					FastVector	attVals;
					Instances	data;	
					double[]	vals;
			
					// 1. Set up attributes
					atts = new FastVector();
					atts.addElement(new Attribute("att1"));
					atts.addElement(new Attribute("att2"));
					atts.addElement(new Attribute("att3"));
					atts.addElement(new Attribute("att4"));
					atts.addElement(new Attribute("att5"));
					atts.addElement(new Attribute("att6"));
					atts.addElement(new Attribute("att7"));
					attVals = new FastVector();
					attVals.addElement("High");
					attVals.addElement("Low");
					attVals.addElement("Medium");
					atts.addElement(new Attribute("CLASS", attVals));
						
					// 2. Create Instances object
					data = new Instances("Test", atts, 0);
					
					// 3. Fill with data (only one instance) + Standardization
					vals = new double[data.numAttributes()];
					vals[0] = (C1(inputSequence) - C1mean)/C1SD; 
					vals[1] = (C2(inputSequence) - C2mean)/C2SD;
					vals[2] = (C3(inputSequence) - C3mean)/C3SD;
					vals[3] = (C4 - C4mean)/C4SD;			// incomplete --- //SeqRatt%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					/* incomplete --- 
					 * should be vals[3] = ([arg C4] - C4mean)/C4SD;	
					 * where [arg C4] = predicted protein fold rate in log10 based using SeqRate
					 */
					vals[4] = (DP(inputSequence, "CL") - C5mean)/C5SD;
					vals[5] = (DP(inputSequence, "QD") - C6mean)/C6SD;
					vals[6] = (DP(inputSequence, "VE") - C7mean)/C7SD;
					
					data.add(new Instance(1.0, vals));
					
					// 4. Output data
					System.out.println(data);
					
					// 5. Set class index
					data.setClassIndex(data.numAttributes()-1);
					
					// 6. Load Classification Model
					String modelPath = "/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/";
					
					LibSVM Classification = (LibSVM) weka.core.SerializationHelper.read(new FileInputStream(modelPath + "Classification.model"));
					
					// 7. Prepare instance to classify
					Instance toClassifyInstance = (Instance) data.instance(0).copy();
					//System.out.println(toClassifyInstance);
					toClassifyInstance.setClassValue(Instance.missingValue());
					//System.out.println("========" + toClassifyInstance);
					
					double PredClass = Classification.classifyInstance(toClassifyInstance);			
				    	String PredictedClass = toClassifyInstance.classAttribute().value((int) PredClass);
				    	System.out.println("Predicted as " + PredictedClass);
				    	
				    	double[] PredictionProbability = Classification.distributionForInstance(toClassifyInstance);
				    	System.out.println("=== Prediction Probability ===");
				    	System.out.println(Utils.arrayToString(PredictionProbability));
					
				    
				    bwres.write(header + "===" + (new java.text.DecimalFormat("0.00").format(PredictionProbability[0])) + ","  + (new java.text.DecimalFormat("0.00").format(PredictionProbability[1])) + "," + (new java.text.DecimalFormat("0.00").format(PredictionProbability[2])) +  "===" + PredictedClass + "===");
				    bwres.flush();

				    
				    
					/**
					 * the run the regression model
					 */
				    	if (PredClass == 0.0) 
				    	{
				     	// === REGRESSION HIGH ===
				    		FastVector	attsRH;
				    		Instances	dataRH;
				    		double[]	valsRH;
				    		
				    		// 1. Set up attributes
				    		attsRH = new FastVector();
				    		attsRH.addElement(new Attribute("attRH1"));
				    		attsRH.addElement(new Attribute("attRH2"));
				    		attsRH.addElement(new Attribute("attRH3"));
				    		attsRH.addElement(new Attribute("RH"));
				    		
				    		// 2. Create Instances object
				    		dataRH = new Instances("TestRH", attsRH, 0);
				    		
				    		// 3. Fill with data (ONE instance only) + Standardization
				    		valsRH = new double[dataRH.numAttributes()];
				    		valsRH[0] = (DP(inputSequence, "TP") - H1mean)/H1SD;
				    		valsRH[1] = (DP(inputSequence, "VT") - H2mean)/H2SD;
				    		valsRH[2] = (H3(inputSequence) - H3mean)/H3SD;
				    		
				    		dataRH.add(new Instance(1.0, valsRH));
				    		
				    		// 4. Output data
				    		System.out.println(dataRH);
				    		
				    		// 5. Set Class index
				    		dataRH.setClassIndex(dataRH.numAttributes()-1);
				    		
				    		// 6. Load Regression High Model
				    		Classifier Regress = (Classifier) weka.core.SerializationHelper.read("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/High.model");
				    		
				    		 // 7. Prepare instance to classify
				    		Instance toClassifyInstanceRH = (Instance) dataRH.instance(0).copy();
				    		System.out.println(toClassifyInstanceRH);
				    		toClassifyInstanceRH.setClassValue(Instance.missingValue());
				    		
				    		// 8. Predict
				       	double Pred = Regress.classifyInstance(toClassifyInstanceRH);
				    		double PredV = roundToDecimals(Pred,4);
					    	System.out.println("Predicted expression level = " + PredV + "mg/L");
					   
					    	bwres.write(PredV + "\n");
						bwres.flush();
						
				    	}else if (PredClass == 1.0)
				    	{
				    			// === REGRESSION LOW === 	
				    			FastVector	attsRL;
				    			Instances	dataRL;
				    			double[]	valsRL;
				    			
				    			// 1. Set up attributes
				    			attsRL = new FastVector();
				    			attsRL.addElement(new Attribute("attRL1"));
				    			attsRL.addElement(new Attribute("attRL2"));
				    			attsRL.addElement(new Attribute("attRL3"));
				    			attsRL.addElement(new Attribute("attRL4"));
				    			attsRL.addElement(new Attribute("RL"));
				    			
				    			// 2. Create Instances object
				    			dataRL = new Instances("TestRL", attsRL, 0);
				    			
				    			// 3. Fill with data (ONE instance only) + Standardization
				    			valsRL = new double[dataRL.numAttributes()];
				    			valsRL[0] = (L1a(inputSequence)*L1b - L1mean)/ L1SD;		// incomplete --- 
				    			/* incomplete --- 
				    			 * should be valsRL[0] = (L1a(inputSequence)*[arg L1b] - L1mean)/ L1SD;
				    			 * where [arg L1b] = predicted protein fold rate in %%%%log10 based%%%%%% using SeqRate %%%%%%%%%valsRL[0] = (L1a(inputSequence) *L1b - L1mean)/ L1SD;
				    			 */
				    			valsRL[1] = (L2(inputSequence) - L2mean)/ L2SD;
				    			valsRL[2] = (L3a(inputSequence)*L3b - L3mean)/ L3SD;		// incomplete ---  %%%%%%%%%%%%valsRL[2] = (L3a(inputSequence)*L3b (from TMHMM) - L3mean)/ L3SD;
				    			/* incomplete --- 
				    			 * should be valsRL[2] = (L3a(inputSequence)*[arg L3b] - L3mean)/ L3SD;
				    			 * where [arg L3b] = predicted number of occurrence of transmembrane using TMHMM
				    			 */
				    			valsRL[3] = (L4a(inputSequence)*L4b - L4mean)/ L4SD;		// incomplete --- 
				    			/* incomplete --- 
				    			 * should be valsRL[3] = (L4a(inputSequence)*[arg L4b] - L4mean)/ L4SD;
				    			 * where [arg L4b] = predicted protein fold rate in %%%%natural log%%%%% using SeqRate %%%%%%%%%%%%%%%%%%valsRL[3] = (L4a(inputSequence)*L1b - L4mean)/ L4SD;
				    			 */
				    			
				    			dataRL.add(new Instance(1.0, valsRL));
				    			
				    			// 4. Output data
				    			System.out.println(dataRL);
				    			
				    			// 5. Set class index
				    			dataRL.setClassIndex(dataRL.numAttributes()-1);
				    			
				    			// 6. Load Regression Low Model
				    			Classifier Regress = (Classifier) weka.core.SerializationHelper.read("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/Low.model");	
				    			
				    			// 7. Prepare instance to classify
				    			Instance toClassifyInstanceRL = (Instance) dataRL.instance(0).copy();
				    			System.out.println(toClassifyInstanceRL);
				        		toClassifyInstanceRL.setClassValue(Instance.missingValue());
				    		
				        		// 8. Predict
				           		double Pred = Regress.classifyInstance(toClassifyInstanceRL);
				        		if (Pred < 0.0){
				    				Pred = 0.0;
				    			}
					    		double PredV = roundToDecimals(Pred,4);
					    		System.out.println("Predicted expression level = " + PredV + "mg/L");
						    	
					    		bwres.write(PredV + "\n");
							bwres.flush();
					    		
				    		} else 
				    		{
				    			// === REGRESSION MEDIUM === 
				    			
				    			FastVector	attsRM;
				    			Instances	dataRM;
				    			double[]	valsRM;
				    			
				    			// 1. Set up attributes
				    			attsRM = new FastVector();
				    			attsRM.addElement(new Attribute("attRM1"));
				    			attsRM.addElement(new Attribute("attRM2"));
				    			attsRM.addElement(new Attribute("attRM3"));
				    			attsRM.addElement(new Attribute("attRM4"));
				    			attsRM.addElement(new Attribute("attRM5"));
				    			attsRM.addElement(new Attribute("attRM6"));
				    			attsRM.addElement(new Attribute("RM"));
				    			
				    			// 2. Create Instances object
				    			dataRM = new Instances("TestRM", attsRM, 0);
				    			
				    			// 3. Fill with data (ONE instance only) + Standardization
				    			valsRM = new double[dataRM.numAttributes()];
				    			valsRM[0] = (DP(inputSequence, "ER") - M1mean)/M1SD;
				    			valsRM[1] = (DP(inputSequence, "WQ") - M2mean)/M2SD;
				    			valsRM[2] = (DP(inputSequence, "VT") - M3mean)/M3SD;
				    			valsRM[3] = (M4(inputSequence) - M4mean)/M4SD;
				    			valsRM[4] = (M5(inputSequence) - M5mean)/M5SD;
				    			valsRM[5] = (M6a(inputSequence)*M6b - M6mean)/M6SD;			// incomplete
				    			/* incomplete --- 
				    			 * should be valsRM[5] = (M6a(inputSequence)* [arg M6b] - M6mean)/ M6SD
				    		 * where [arg M6b] = Isoelectric point calculated using ExPASy %%%%%%%%%%%%%valsRM[5] = (M6a(inputSequence)*M6b - M6mean)/M6SD;
				    			*/
				    			
				    		dataRM.add(new Instance(1.0, valsRM));
				    			
				    		// 4. Output data
				    		System.out.println(dataRM);
				    			
				    		// 5. Set class index
				    		dataRM.setClassIndex(dataRM.numAttributes()-1);
				    			
				    		// 6. Load Regression Low Model
				    		Classifier Regress = (Classifier) weka.core.SerializationHelper.read("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/Medium.model");	
				    			
				    		// 7. Prepare instance to classify
				    		Instance toClassifyInstanceRM = (Instance) dataRM.instance(0).copy();
				    		System.out.println(toClassifyInstanceRM);
				        	toClassifyInstanceRM.setClassValue(Instance.missingValue());
				    		
				        	// 8. Predict
				        	double Pred = Regress.classifyInstance(toClassifyInstanceRM);
				        	double PredV = roundToDecimals(Pred,4);
				    	    	System.out.println("Predicted expression level = " + PredV + "mg/L");
				    	    	
				    	    	bwres.write(PredV + "\n");
						bwres.flush();
				    	} 
				    	
				    	
				}
			}
			
			bwres.close();
	    		fwres.close();
		}
		catch (Exception e)
		{
			File log = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/log.txt");
			try
			{
				FileWriter fw = new FileWriter(log, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("expasy and Catherine's code===============\n");
				bw.write(e.getMessage() + "\n");
				bw.flush();
				bw.close();
				fw.close();
			}
			catch(Exception estdio)
			{
				estdio.printStackTrace();
			}
		}
		
		try
		{
			File done = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/done.txt");
			FileWriter fw = new FileWriter(done);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("done");
			bw.flush();
			bw.close();
			fw.close();
		}catch(Exception e)
		{
			File log = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/log.txt");
			try
			{
				FileWriter fw = new FileWriter(log, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("done.txt===============\n");
				bw.write(e.getMessage() + "\n");
				bw.flush();
				bw.close();
				fw.close();
			}
			catch(Exception estdio)
			{
				estdio.printStackTrace();
			}
		}
		
		
		try
		{
			EmailResultSopep e = new EmailResultSopep();
			e.setParams(email, folderName, taskName);
			e.sendEmail();
		}catch(Exception e)
		{
			File log = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/log.txt");
			try
			{
				FileWriter fw = new FileWriter(log, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("email to user===============\n");
				bw.write(e.getMessage() + "\n");
				bw.flush();
				bw.close();
				fw.close();
			}
			catch(Exception estdio)
			{
				estdio.printStackTrace();
			}
		}
	}
	
	public static double roundToDecimals(double d, int c)
	{
	    int temp = (int)((d*Math.pow(10, c)));		// temp = d * 10^c
	    return (((double)temp)/Math.pow(10,c));		
	}

	public static double C1 (String inputSequence) {
		
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'H' || inputSequence.charAt(i) == 'K' || inputSequence.charAt(i) == 'R')  {
				counter++;
			}
			}
		
		double output = (double) counter/inputSequence.length();
		return output;
	}

	public static double C2 (String inputSequence) {
		
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'C' || inputSequence.charAt(i) == 'M')  {
				counter++;
			}
			}
		
		double output = (double) counter/inputSequence.length();
		return output;
	}

	public static double C3 (String inputSequence) {
		
		List<Integer> indexes = new ArrayList<Integer>();
		for( int i = -1; (i = inputSequence.indexOf("H", i+1)) != -1;) {
			indexes.add(i);
			}
		for( int j = -1; (j = inputSequence.indexOf("K", j+1)) != -1;) {
			indexes.add(j);
			}
		for( int j = -1; (j = inputSequence.indexOf("R", j+1)) != -1;) {
			indexes.add(j);
			}
		
		int[] IndArray = new int[indexes.size()];
		for (int a = 0; a < IndArray.length; a++) {
			IndArray[a] = indexes.get(a);
			}
		
		Arrays.sort(IndArray);
		int z = 0;
		int[] conse = new int[IndArray.length - 1];
		for (int y = 0; y < IndArray.length - 1; y++) {
		conse[y] = IndArray[z+1] - IndArray[z];
		z++;
			}
		
		int count = 0;
		int maxCount = 0;
		for (int x = 0; x < conse.length; x++) {
			if (conse[x] == 1) {
				count++;
			} else {
				count = 0;				
			}
			
			if (count > maxCount) {
				maxCount = count;
			}
		}
		
		double MaxConseGroup = (double) maxCount + 1;
		return MaxConseGroup;
	}

	public static double DP (String inputSequence, String DP) {
		
		int lastIndex = 0;
		int count = 0;
		
		while(lastIndex != -1) {
			lastIndex = inputSequence.indexOf(DP,lastIndex);
			if (lastIndex != -1) {
				count++;
				lastIndex+=DP.length();
			}
		}
		return count;		
	}

	public static double H3 (String inputSequence) {
		
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'T')  {
				counter++;
				}
			}
		
		double v35 = (double) counter/inputSequence.length();
		int maxCount = 1;
		String s1 = "";
		if (inputSequence.toLowerCase().contains("F".toLowerCase())){	
		for(int i = 0; i < inputSequence.length(); i++) {
			s1 = s1 + "F";
			if (inputSequence.toLowerCase().contains(s1.toLowerCase())){
				maxCount = s1.length();
				}
			}
		} else {
			maxCount = 0;
		}
			double v69 = (double) maxCount;
			double H3 = v35*v69;
			return H3;
	}

	public static double L1a (String inputSequence) {
		
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'F')  {
				counter++;
			}
			}
		
		double L1a = (double) counter/inputSequence.length();
		return L1a;
	}

	public static double L2 (String inputSequence) {
		
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'S')  {
				counter++;
				}
			}
		
		double v33 = (double) counter/inputSequence.length();
		List<Integer> indexes = new ArrayList<Integer>();
		for( int i = -1; (i = inputSequence.indexOf("I", i+1)) != -1;) {
			indexes.add(i);
			}
		for( int j = -1; (j = inputSequence.indexOf("L", j+1)) != -1;) {
			indexes.add(j);
			}
		for( int j = -1; (j = inputSequence.indexOf("V", j+1)) != -1;) {
			indexes.add(j);
			}
		for( int j = -1; (j = inputSequence.indexOf("A", j+1)) != -1;) {
			indexes.add(j);
			}
		for( int j = -1; (j = inputSequence.indexOf("G", j+1)) != -1;) {
			indexes.add(j);
			}
		for( int j = -1; (j = inputSequence.indexOf("P", j+1)) != -1;) {
			indexes.add(j);
			}
		int[] IndArray = new int[indexes.size()];
		for (int a = 0; a < IndArray.length; a++) {
			IndArray[a] = indexes.get(a);
		}
		Arrays.sort(IndArray);
		int z = 0;
		int[] conse = new int[IndArray.length - 1];
		for (int y = 0; y < IndArray.length - 1; y++) {
		conse[y] = IndArray[z+1] - IndArray[z];
		z++;
		}
		int count = 0;
		int maxCount = 0;
		for (int x = 0; x < conse.length; x++) {
			if (conse[x] == 1) {
				count++;
			} else {
				count = 0;				
			}
			
			if (count > maxCount) {
				maxCount = count;
				}
			}
		
			double v84 = (double) maxCount + 1;
			double L2 = v33*v84;
			return L2;
	}

	public static double L3a (String inputSequence) {
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'Y')  {
				counter++;
			}
			}
		double output = (double) counter;
		return output;
	}

	public static double L4a (String inputSequence) {
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'Y')  {
				counter++;
			}
			}
		double output = (double) counter/inputSequence.length();
		return output;
	}

	public static double M4 (String inputSequence) {
		
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'R')  {
				counter++;
				}
			}
		
		double A = (double) counter/inputSequence.length();
		int counterR = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'R')  {
				counterR++;
				}
			}
		int counterK = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'K')  {
				counterK++;
				}
			}
		int counterD = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'D')  {
				counterD++;
				}
			}
		int counterE = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'E')  {
				counterE++;
				}
			}
		
		double B = (double) (counterR + counterK - counterD - counterE) /inputSequence.length() - 0.03;
		double M4 = A*B;
		return M4;
	}

	public static double M5 (String inputSequence) {
		
		int counter = 0;
		for(int i = 0; i < inputSequence.length(); i++) {
			if (inputSequence.charAt(i) == 'E' || inputSequence.charAt(i) == 'D')  {
				counter++;
				}
			}
		
		double A = (double) counter/inputSequence.length();
		List<Integer> indexes = new ArrayList<Integer>();
		for( int i = -1; (i = inputSequence.indexOf("I", i+1)) != -1;) {
			indexes.add(i);
			}
		for( int j = -1; (j = inputSequence.indexOf("L", j+1)) != -1;) {
			indexes.add(j);
			}
		for( int j = -1; (j = inputSequence.indexOf("V", j+1)) != -1;) {
			indexes.add(j);
			}
		for( int j = -1; (j = inputSequence.indexOf("A", j+1)) != -1;) {
			indexes.add(j);
			}
		for( int j = -1; (j = inputSequence.indexOf("G", j+1)) != -1;) {
			indexes.add(j);
			}
		int[] IndArray = new int[indexes.size()];
		for (int a = 0; a < IndArray.length; a++) {
			IndArray[a] = indexes.get(a);
			}
		Arrays.sort(IndArray);
		int z = 0;
		int[] conse = new int[IndArray.length - 1];
		for (int y = 0; y < IndArray.length - 1; y++) {
		conse[y] = IndArray[z+1] - IndArray[z];
		z++;
			}
		int count = 0;
		int maxCount = 0;
		for (int x = 0; x < conse.length; x++) {
			if (conse[x] == 1) {
				count++;
			} else {
				count = 0;				
			}
			
			if (count > maxCount) {
				maxCount = count;
			}
		}
		
			double B = (double) maxCount + 1;
			double M5 = A*B;
			return M5;
	}

	public static double M6a (String inputSequence) {
		int maxCount = 1;
		String s1 = "";
		if (inputSequence.toLowerCase().contains("C".toLowerCase())) {
		for(int i = 0; i < inputSequence.length(); i++) {
			s1 = s1 + "C";
			if (inputSequence.toLowerCase().contains(s1.toLowerCase())){
				maxCount = s1.length();
				}		
			} 
		} else {
			maxCount = 0;
		}
			double M6a = (double) maxCount;
			return M6a;
	}
}
