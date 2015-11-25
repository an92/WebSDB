package Beans;

import java.io.*;

public class changeResult 
{
	private String pathName = "";
	private String ion ="";
	
	public void setParams(String path, String ion)
	{
		this.ion = ion;
		pathName = path; 
	}
	
	public void change() throws Exception
	{
		double threshold = 0.0;
		if(ion.equals("Ca"))
			threshold = -0.376;
		else if(ion.equals("Co"))
			threshold = -0.382;
		else if(ion.equals("Cu"))
			threshold = -0.056;
		else if(ion.equals("Fe"))
			threshold = -0.086;
		else if(ion.equals("Mg"))
			threshold = -0.230;
		else if(ion.equals("Mn"))
			threshold = -0.315;
		else if (ion.equals("Ni"))
			threshold = -0.050;
		else if (ion.equals("Zn"))
			threshold = -0.092;
		else
			threshold = 0.0;
		
		System.out.println("changing results...");
		File files[] = new File(pathName).listFiles();
		for(int i = 0 ; i < files.length; i++)
		{
			if(files[i].getName().startsWith("output_") && files[i].isDirectory())
			{
				File results[] = files[i].listFiles();
				for(int j = 0 ; j < results.length; j++)
				{
					if(results[j].getName().equals(ion + "_predict.txt")) 
					{
						File out = new File(pathName + "/new_predict.txt");
						FileWriter fw = new FileWriter(out);
						BufferedWriter bw = new BufferedWriter(fw);
						
						FileReader fr = new FileReader(results[j]);
						BufferedReader br = new BufferedReader(fr);
						String str = br.readLine();
						while(str!=null)
						{
							str = str.trim();
							bw.write(str.split("\t")[0].split("_")[4].trim() + "\t" + (Double.parseDouble(str.split("\t")[1].trim())+2*Math.abs(threshold) + 0.5)+ "\n");
							bw.flush();
							str = br.readLine();
						}
						br.close();
						fr.close();
						bw.close();
						fw.close();
						break;
					}
				}
				
			}	
		}
	}
}
