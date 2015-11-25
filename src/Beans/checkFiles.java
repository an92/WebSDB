package Beans;

import java.io.*;


public class checkFiles 
{
	private String pathName = "";
	private String pdb = "";
	private String chain = "";
	
	public void setParams(String path, String pdb, String chain)
	{
		this.pathName = path; 
		this.pdb = pdb;
		this.chain = chain;
	}
	
	public String check() throws Exception
	{
		String results = "";
		
		//check sequence fasta file
		File file = new File(pathName + "/" + pdb + "_" + chain + ".fasta.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str = br.readLine();
		
		int count = 0;
		String flag = "true";
		while(str!=null)
		{
			str = str.trim();
			if(str.startsWith(">"))
				count++;
			else
			{
				for(int i = 0 ; i < str.length(); i++)
				{
					if(!str.substring(i,i+1).equals("A") && !str.substring(i,i+1).equals("C") && !str.substring(i,i+1).equals("D") && !str.substring(i,i+1).equals("E") && !str.substring(i,i+1).equals("F") && !str.substring(i,i+1).equals("G") && !str.substring(i,i+1).equals("H") && !str.substring(i,i+1).equals("I") && !str.substring(i,i+1).equals("K") && !str.substring(i,i+1).equals("L") && !str.substring(i,i+1).equals("M") && !str.substring(i,i+1).equals("N") && !str.substring(i,i+1).equals("P") && !str.substring(i,i+1).equals("Q") && !str.substring(i,i+1).equals("R") && !str.substring(i,i+1).equals("S") && !str.substring(i,i+1).equals("T") && !str.substring(i,i+1).equals("W") && !str.substring(i,i+1).equals("Y") && !str.substring(i,i+1).equals("V"))			
					{
						flag = "false";
						System.out.println(str.substring(i,i+1));
					}
				}
			}
			str =br.readLine();
		}
		br.close();
		fr.close();
		results = "fasta===" + count + ",aa===" + flag;
		
		return results;
	}
}
