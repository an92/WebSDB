package Beans;

import java.io.*;


public class CheckSeq 
{
	
	public boolean checkFormat(File file, String folderName) throws Exception
	{
		System.out.println(file.getAbsolutePath());
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String str = br.readLine();
		int count = 0;
		int lineCount = 0;
		String old = "";
		while(str!=null)
		{
			str = str.trim();
			if(!str.equals(""))
			{
				if(str.startsWith(">"))
				{
					count++;
					if(old.startsWith(">"))
					{
						br.close();
						fr.close();
						return false;
					}
					else
					{
						old = str;
					}
				}
				else
				{
					lineCount++;
					if(!old.startsWith(">"))
					{
						br.close();
						fr.close();
						return false;
					}
					else
					{
						old = str;
						for(int i = 0 ; i < str.length(); i++)
						{
							if(!str.substring(i,i+1).equals("A") && !str.substring(i,i+1).equals("C") && !str.substring(i,i+1).equals("D") && !str.substring(i,i+1).equals("E") && !str.substring(i,i+1).equals("F") && !str.substring(i,i+1).equals("G") && !str.substring(i,i+1).equals("H") && !str.substring(i,i+1).equals("I") && !str.substring(i,i+1).equals("K") && !str.substring(i,i+1).equals("L") && !str.substring(i,i+1).equals("M") && !str.substring(i,i+1).equals("N") && !str.substring(i,i+1).equals("P") && !str.substring(i,i+1).equals("Q") && !str.substring(i,i+1).equals("R") && !str.substring(i,i+1).equals("S") && !str.substring(i,i+1).equals("T") && !str.substring(i,i+1).equals("W") && !str.substring(i,i+1).equals("Y") && !str.substring(i,i+1).equals("V"))			
							{
								br.close();
								fr.close();
								return false;
							}
						}
					}
				}
			}
			str = br.readLine();
		}
		br.close();
		fr.close();
		System.out.println(count + "  " + lineCount);
		
		if(count > 0 && count==lineCount)
			return true;
		else
		{
			return false;
		}
	}
	
}
