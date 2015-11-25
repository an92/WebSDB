package Beans;

import java.io.*;

public class SplitSeqSopep 
{
	public void splitSeq(String folderName) throws Exception
	{
		File file = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/out.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		
		
		String str = br.readLine();
		int i = 0;
		while(str!=null)
		{
			str = str.trim();
			if(str.startsWith(">"))
			{
				File dir1 = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/fasta/");
				dir1.mkdir();
				
				File dir2 = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/fasta/" + i);
				dir2.mkdir();
				
				File out = new File("/var/lib/tomcat7/webapps/periscope/users/" + folderName + "/fasta/" + i+ ".fasta");
				FileWriter fw = new FileWriter(out);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(str + "\n" + br.readLine());
				bw.flush();
				bw.close();
				fw.close();
				
				i++;
			}
			str = br.readLine();
		}
		br.close();
		fr.close();
	}
}
