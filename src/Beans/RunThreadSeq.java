package Beans;

import java.io.*;

import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;

public class RunThreadSeq implements Runnable
{
	private String folderName = "";
	private String email =  "";
	private String taskName = "";
	private static String homeFolder = "/var/lib/tomcat7/webapps/PeptideExplorer/users/";
	
	public void setFolder(String folderName, String taskName, String email)
	{
		this.folderName = folderName;
		this.email = email;
		this.taskName = taskName;
	}
	
	public void run()
	{
		try
		{
			//generate index file
			File index = new File(homeFolder + folderName + "/index.tsv");
			FileWriter fwindex = new FileWriter(index);
			BufferedWriter bwindex = new BufferedWriter(fwindex);
			
			File txt = new File(homeFolder + folderName + "/fasta/seq.txt");
			FileReader frtxt = new FileReader(txt);
			BufferedReader brtxt = new BufferedReader(frtxt);
			
			String stxt = brtxt.readLine();
			String header = "";
			String seq = "";
			String type = "";
			while(stxt!=null)
			{
				stxt = stxt.trim();
				if(stxt.startsWith(">"))
				{
					if(!header.equals("") && !seq.equals("") && !type.equals(""))
					{
						bwindex.write(header + "\t" + seq + "\t" + type + "\n");
						bwindex.flush();
						header = stxt.substring(1);
						header = header.replaceAll("\t", " ");
					}
					else
					{
						header = stxt.substring(1);
						header = header.replaceAll("\t", " ");
					}
				}
				else
				{
					seq = stxt;
					if(seq.length() >=20)
						type = "long";
					else
						type = "short";
				}
				stxt = brtxt.readLine();
			}
			if(!header.equals("") && !seq.equals("") && !type.equals(""))
			{
				bwindex.write(header + "\t" + seq + "\t" + type + "\n");
				bwindex.flush();
			}
			bwindex.close();
			fwindex.close();
			brtxt.close();
			frtxt.close();
			
			//generate the attribute file for weka implementation
			ProcessBuilder builder = new ProcessBuilder("perl", homeFolder +  folderName + "/final3.pl", homeFolder +  folderName + "/");
		    final Process process = builder.start();
		    new Thread(new Runnable(){
		        public void run(){
		         
		         try{
		         InputStreamReader eisr = new InputStreamReader(process
		           .getErrorStream());
		         BufferedReader ebr = new BufferedReader(eisr);
		        
		         String eoutStr = ebr.readLine();
		         while (eoutStr != null) {
		        	 System.out.println(eoutStr);
		          eoutStr = ebr.readLine();     
		         }
		         }catch(Exception e){

		          e.printStackTrace();
		         }
		        }
		       }).start();
		    
		    new Thread(new Runnable(){
		        public void run(){
		         
		         try{
		         InputStreamReader eisr = new InputStreamReader(process
		           .getInputStream());
		         BufferedReader ebr = new BufferedReader(eisr);
		        
		         String eoutStr = ebr.readLine();
		         while (eoutStr != null) {
		        	 System.out.println(eoutStr);
		          eoutStr = ebr.readLine();     
		         }
		         }catch(Exception e){

		          e.printStackTrace();
		         }
		        }
		       }).start();
			process.waitFor();
			
			//load weka model to predict and record the results into result.csv file.
			ObjectInputStream ois = new ObjectInputStream(
	                new FileInputStream(homeFolder + folderName + "/F0L.model"));
			RandomForest shortRF = (RandomForest) ois.readObject();
			ois.close();
			
			ois = new ObjectInputStream(
	                new FileInputStream(homeFolder + folderName + "/F4S.model"));
			RandomForest longRF = (RandomForest) ois.readObject();
			ois.close();
			
			File file = new File(homeFolder + folderName + "/seq.arff");
			ArffLoader csv = new ArffLoader();
			csv.setFile(file);
			
			Instances samples = csv.getStructure();
			samples.setClassIndex(samples.numAttributes()-1);
			
			File out = new File(homeFolder +folderName + "/result.tsv");
			FileWriter fw = new FileWriter(out);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("Fasta header\tPeptide sequence\tMatrix\tDecision");
			
			File findex = new File(homeFolder + folderName + "/index.tsv");
			FileReader fr = new FileReader(findex);
			BufferedReader br = new BufferedReader(fr);
			String info = br.readLine();
			
			//System.out.println(samples.numInstances());
			Instance sample = csv.getNextInstance(samples);
			while(sample!=null)
			{
				System.out.println(info);
				if(info.split("\t")[2].equals("short"))
				{
					System.out.println(shortRF.classifyInstance(sample));
					//System.out.println(shortRF.distributionForInstance(samples.instance(i))[0] + "  " + shortRF.distributionForInstance(samples.instance(i))[1]);
					double matrix0 = shortRF.distributionForInstance(sample)[0];
					double matrix1 = shortRF.distributionForInstance(sample)[1];
					if(Utils.grOrEq(matrix0, matrix1))
						bw.write(info + "\t" + matrix0 + "\t" + matrix1 +"\tActive\n");
					else
						bw.write(info + "\t" + matrix0 + "\t" + matrix1 +"\tInactive\n");
					bw.flush();
				}
				else
				{
					System.out.println(longRF.classifyInstance(sample));
					//System.out.println(longRF.distributionForInstance(samples.instance(i))[0] + "  " + longRF.distributionForInstance(samples.instance(i))[1]);
					double matrix0 = longRF.distributionForInstance(sample)[0];
					double matrix1 = longRF.distributionForInstance(sample)[1];
					if(Utils.grOrEq(matrix0, matrix1))
						bw.write(info + "\t" + matrix0 + "\t" + matrix1 +"\tActive\n");
					else
						bw.write(info + "\t" + matrix0 + "\t" + matrix1 +"\tInactive\n");
					bw.flush();
				}
				sample = csv.getNextInstance(samples);
				info = br.readLine();
			}
			br.close();
			fr.close();
			bw.close();
			fw.close();
			
			//generate a empty file named "done"
			File done = new File(homeFolder  + folderName + "/done.txt");
			FileWriter fwd = new FileWriter(done);
			BufferedWriter bwd = new BufferedWriter(fwd);
			bwd.write("done");
			bwd.flush();
			bwd.close();
			fwd.close();
			
			//Then email to user with attached result file
			EmailSeqResult emailSend = new EmailSeqResult();
			emailSend.setParams(email, folderName, taskName);
			emailSend.sendEmail();
			
			
			
		}catch(Exception e)
		   {
			   e.printStackTrace();
		   }
	}
	
	
}
