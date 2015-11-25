package Beans;

import java.io.*;

public class CheckSopepSeq 
{
	public boolean checkFormat(File file, String folderName) throws Exception
	{
		// TODO Auto-generated method stub
				File out = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/out.txt");
				FileWriter fw = new FileWriter(out);
				BufferedWriter bw = new BufferedWriter(fw);
				
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				int headCounter = 0;
				String seq = "";
				String str = br.readLine();
				int seqFlag = 0;
				int seqCounter = 0;
				
				while(str!=null)
				{
					str = str.trim();
					if(str.startsWith(">"))
					{
						headCounter ++;
						
						if(!seq.equals(""))
						{
							seqCounter ++;
							bw.write(seq + "\n");
							bw.flush();
						}
						
						bw.write(str + "\n");
						bw.flush();
						seq = "";
					}
					else
					{
						str = str.toUpperCase();
						for(int i = 0 ; i < str.length(); i++)
						{
							if(!str.substring(i,i+1).equals("A") && !str.substring(i,i+1).equals("C") && !str.substring(i,i+1).equals("D") && !str.substring(i,i+1).equals("E") && !str.substring(i,i+1).equals("F") && !str.substring(i,i+1).equals("G") && !str.substring(i,i+1).equals("H") && !str.substring(i,i+1).equals("I") && !str.substring(i,i+1).equals("K") && !str.substring(i,i+1).equals("L") && !str.substring(i,i+1).equals("M") && !str.substring(i,i+1).equals("N") && !str.substring(i,i+1).equals("P") && !str.substring(i,i+1).equals("Q") && !str.substring(i,i+1).equals("R") && !str.substring(i,i+1).equals("S") && !str.substring(i,i+1).equals("T") && !str.substring(i,i+1).equals("W") && !str.substring(i,i+1).equals("Y") && !str.substring(i,i+1).equals("V"))			
							{
								seqFlag ++;
							}
						}
						seq = seq + str;
					}
					System.out.println(str);
					str = br.readLine();
				}
				
				if(!seq.equals(""))
				{
					seqCounter ++;
					bw.write(seq + "\n");
					bw.flush();
				}
				
				bw.close();
				fw.close();
				
				br.close();
				fr.close();
				
				
				System.out.println(headCounter + "=========" + seqCounter);
				
				if(headCounter>5)
				{
					return false;
				}
				
				
				if(seqFlag !=0)
				{
					return false;
				}
				
				if(headCounter != seqCounter)
				{
					return false;
				}
				
				return true;
	}
}
