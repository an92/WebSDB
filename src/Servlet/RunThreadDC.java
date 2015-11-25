package Servlet;

import java.io.*;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class RunThreadDC implements Runnable
{
	private String drugA = "";
	private String drugB = "";
	private String folderName = "";
	
	public void setDrug(String drugA, String drugB, String folderName)
    {
		this.drugA = drugA;
		this.drugB = drugB;
		this.folderName = folderName;
    } 
	
	public void run()
	{
		//First let's find the information of these two drugs regarding to the selected features including ATC code, experiment results, molecular weight and chemical similarity.
		try
		{
			String idA = "";
			String idB = "";
			String infoA = "";
			String infoB = "";
			String nameA = "";
			String nameB = "";
			String drugBankA = "";
			String drugBankB = "";
			String ncbiA = "";
			String ncbiB = "";
			
			File file = new File ("/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/drugData.csv");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str = br.readLine();
			
			while(str!=null)
			{
				str = str.trim();
				String name = str.split(",")[0].trim();
				if(name.equals(drugA))
				{
					//System.out.println(str);
					String splits[] = str.split(",");
					infoA = splits[3].trim() + "," + splits[4].trim() + "," + splits[5].trim() + "," + splits[6].trim() + "," + splits[7].trim() + "," + splits[8].trim();
					idA = splits[2].trim();
					nameA = splits[0].trim();
					infoA = infoA.replaceAll("missing", "?");
					drugBankA = splits[1].trim();
					if(!drugBankA.startsWith("DB"))
						drugBankA = "unknown";
					ncbiA = splits[2].trim();
					System.out.println(infoA + "   " + idA);
				}
				if(name.equals(drugB))
				{
					//System.out.println(str);
					String splits[] = str.split(",");
					infoB = splits[3].trim() + "," + splits[4].trim() + "," + splits[5].trim() + "," + splits[6].trim() + "," + splits[7].trim() + "," + splits[8].trim();
					idB = splits[2].trim();
					nameB = splits[0].trim();
					infoB = infoB.replaceAll("missing", "?");
					drugBankB = splits[1].trim();
					if(!drugBankB.startsWith("DB"))
						drugBankB = "unknown";
					ncbiB = splits[2].trim();
					System.out.println(infoB + "   " +idB); 
				}
				str = br.readLine();
			}
			br.close();
			fr.close();
			
			//calculate similarity of drug A and drug B
			String similarity = "";
			if(!infoA.equals("") && !idA.equals("") && !infoB.equals("") && !idB.equals(""))
			{
				if(new File("/var/lib/tomcat7/webapps/smolcom/sdf/CID_" + idA + ".sdf").exists() && new File("/var/lib/tomcat7/webapps/smolcom/sdf/CID_" + idB + ".sdf").exists())
				{
					Process copy = Runtime.getRuntime().exec("cp /var/lib/tomcat7/webapps/smolcom/sdf/CID_" + idA + ".sdf /var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/");
					copy.waitFor();
					copy = Runtime.getRuntime().exec("cp /var/lib/tomcat7/webapps/smolcom/sdf/CID_" + idB + ".sdf /var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/");
					copy.waitFor();
					
					System.out.println("Rscript /var/lib/tomcat7/webapps/smolcom/tanimoto.R " + "/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/CID_" + idA + ".sdf"  + " " + "/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/CID_" + idB + ".sdf");
					
					
					Process process = Runtime.getRuntime().exec(new String[]{"bash","--login","-c","Rscript /var/lib/tomcat7/webapps/smolcom/tanimoto.R " + "/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/CID_" + idA + ".sdf"  + " " + "/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/CID_" + idB + ".sdf"});
					//Process process = Runtime.getRuntime().exec("java /var/lib/tomcat7/webapps/DrugCombination/test/test");
					//System.out.println("Rscript /var/lib/tomcat7/webapps/DrugCombination/tanimoto.R " + "/var/lib/tomcat7/webapps/DrugCombination/users/" + folderName + "/CID_" + idA + ".sdf"  + " " + "/var/lib/tomcat7/webapps/DrugCombination/users/" + folderName + "/CID_" + idB + ".sdf");
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					BufferedReader os = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String line = "";
					while ( (line=bufferedReader.readLine()) != null) 
					{
						System.out.println(line.split(" ")[1].trim());
						similarity=line.split(" ")[1].trim();
					}
					
					String err = os.readLine();
					while ( (err=os.readLine()) != null)
					{
						System.out.println(err);
					}
					
					
					process.waitFor();
					bufferedReader.close();
					
					
					
				}
			}
			else
			{
				System.out.println("file doesnt exist");
				
			}
			
			
			//write into arff file
			File out = new File("/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/test.arff");
			FileWriter fw = new FileWriter(out);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("@relation DrugCombination\n\n");
			bw.write("@attribute v1 {J,N,G,R,D,M,C,P,A,S,V,L,H,B}\n");
			bw.write("@attribute v2 numeric\n");
			bw.write("@attribute v3 numeric\n");
			bw.write("@attribute v4 numeric\n");
			bw.write("@attribute v5 numeric\n");
			bw.write("@attribute v6 numeric\n");
			bw.write("@attribute v26 {J,N,G,R,D,M,C,P,A,S,V,L,H,B}\n");
			bw.write("@attribute v27 numeric\n");
			bw.write("@attribute v28 numeric\n");
			bw.write("@attribute v29 numeric\n");
			bw.write("@attribute v30 numeric\n");
			bw.write("@attribute v31 numeric\n");
			bw.write("@attribute v51 numeric\n");
			bw.write("@attribute class {effective,ineffective}\n");
			bw.write("@data\n");
			
			bw.flush();
			
			bw.write(infoA + "," + infoB + "," + similarity + ",effective\n");
			bw.flush();
			bw.close();
			fw.close();
			
			//load model and classify the sample in the test.arff file
			ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/RF16.model"));
			RandomForest rf = (RandomForest) ois.readObject();
			ois.close();
			
			ArffLoader testLoader = new ArffLoader();
			testLoader.setFile(new File("/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/test.arff"));
			Instances test = testLoader.getDataSet();
			test.setClassIndex(test.numAttributes()-1);
			
			Evaluation eval = new Evaluation(test);
			//eval.crossValidateModel(rf, samples, 5, new Random(1));
			eval.evaluateModel(rf, test);
			
			File res = new File("/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/results.csv");
			FileWriter fres = new FileWriter(res);
			BufferedWriter bres = new BufferedWriter(fres);
			
			bres.write("names," + nameA + "," + nameB + "\n");
			bres.flush();
			bres.write("ids," + idA + "," + idB + "\n");
			bres.flush();
			bres.write("infoA," + infoA + "\n");
			bres.flush();
			bres.write("infoB," + infoB + "\n");
			bres.flush();
			bres.write("drugBankA," + drugBankA + "\n");
			bres.flush();
			bres.write("drugBankB," + drugBankB + "\n");
			bres.flush();
			bres.write("ncbiA," + ncbiA + "\n");
			bres.flush();
			bres.write("ncbiB," + ncbiB + "\n");
			bres.flush();
			
			for(int i = 0; i < test.numInstances(); i++)
			{
				System.out.println(rf.distributionForInstance(test.instance(i))[0] + "  " + rf.distributionForInstance(test.instance(i))[1]);
				bres.write("res," + rf.distributionForInstance(test.instance(i))[0] + "," + rf.distributionForInstance(test.instance(i))[1] + "\n");
				bres.flush();
			}
			bres.close();
			fres.close();
			
			File stat = new File("/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/stat.txt");
			FileWriter fstat = new FileWriter(stat);
			BufferedWriter bstat = new BufferedWriter(fstat);
			bstat.write("ok");
			bstat.flush();
			bstat.close();
			fstat.close();
			
		}catch(Exception e)
		{
			
		}
		
		
		
	}
}
