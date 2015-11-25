package Beans;

import java.io.*;

public class runModel 
{
	
	private String folderName = "";
	private String ion = "";
	private String homeFolder = "";
	private String pdb ="";
	private String chain = "";
	
	
	public void setParams(String folder, String pdb, String chain, String ion, String homeFolder)
	{
		folderName = folder;
		this.pdb = pdb;
		this.chain = chain;
		this.ion = ion;
		this.homeFolder = homeFolder;
	}
	
	public String getParams()
	{
		return folderName;
	}
	
	public void runProgram() throws Exception
	{
		System.out.println("running prediction......");
		
		generateInput gen = new generateInput();
		gen.setParams(folderName, pdb, chain, ion, homeFolder);
		gen.generateFile();
		
		/**
		 * here, in the updated version of the web server, we need to get the input files on our own by running different tools
		 */
		//First, write a sh file
		File sh  = new File(homeFolder + "users/" + folderName + "/runThirdPartyTools.sh");
		FileWriter fsh = new FileWriter(sh);
		BufferedWriter bsh = new BufferedWriter(fsh);
		bsh.write("");
		
		
		
		
		
		//run program
		ProcessBuilder builder = new ProcessBuilder("perl", homeFolder + "users/" + folderName + "/all_features_last_0126.pl", homeFolder+"users/" + folderName);

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
		
	}
}
