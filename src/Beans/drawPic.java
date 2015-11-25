package Beans;

import java.io.*;

public class drawPic 
{
	private String pathName = "";
	private String ion ="";
	
	public void setParams(String path, String ion)
	{
		this.ion = ion;
		pathName = path; 
	}
	
	public void draw() throws Exception
	{
		System.out.println("drawing pics...");
		
		double threshold = 0.0;
		if(ion.equals("Ca"))
			threshold = Math.abs(-0.376) + 0.5;
		else if(ion.equals("Co"))
			threshold = Math.abs(-0.382) + 0.5;
		else if(ion.equals("Cu"))
			threshold = Math.abs(-0.056) + 0.5;
		else if(ion.equals("Fe"))
			threshold = Math.abs(-0.086) + 0.5;
		else if(ion.equals("Mg"))
			threshold = Math.abs(-0.230) + 0.5;
		else if(ion.equals("Mn"))
			threshold = Math.abs(-0.315) + 0.5;
		else if (ion.equals("Ni"))
			threshold = Math.abs(-0.050) + 0.5;
		else if (ion.equals("Zn"))
			threshold = Math.abs(-0.092) + 0.5;
		else
			threshold = 0.0;
		
		Process process = Runtime.getRuntime().exec("/usr/local/bin/Rscript " + pathName + "/low.r " + pathName + "/low.jpeg " + pathName + "/new_predict.txt " + ion + " " + threshold);
		System.out.println("Rscript " + pathName + "/low.r " + pathName + "/low.jpeg " + pathName + "/new_predict.txt " + ion + " " + threshold);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			System.out.println(str);
			
		}
		
		process.waitFor();
		
		Process process2 = Runtime.getRuntime().exec("/usr/local/bin/Rscript " + pathName + "/high.r " + pathName + "/high.svg " + pathName + "/new_predict.txt " + ion + " " + threshold);
		BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
		String str2 = "";
		while ((str2=bufferedReader2.readLine()) != null) 
		{
			System.out.println(str2);
			
		}
		
		process2.waitFor();
	}
}
