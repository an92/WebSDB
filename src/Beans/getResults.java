package Beans;

import java.io.*;
import java.math.BigDecimal;

public class getResults 
{
	private String pathName = "";
	private String ion ="";
	
	public void setParams(String path, String ion)
	{
		this.ion = ion;
		pathName = path; 
	}
	
	public String getParams()
	{
		return pathName;
	}
	
	public String getResult() throws Exception
	{
		String result = "";
		System.out.println("Extracting results...");
		System.out.println(pathName);
		File file = new File(pathName + "/new_predict.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str = br.readLine();
		while(str!=null)
		{
			str = str.trim();
			System.out.println(str);
			result = result + str.split("\t")[0].trim() + "\t\t\t" + new BigDecimal(Double.parseDouble(str.split("\t")[1].trim())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue() + "\n";
			str = br.readLine();
		}
		br.close();
		fr.close();
		return result;
	}
}
