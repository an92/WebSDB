package Beans;

import java.io.*;

public class GenerateThirdPartyInputs 
{
	private String folderName = "";
	private String homeFolder = "/var/lib/tomcat7/webapps/ROOT/";
	private String pdb ="";
	private String chain = "";
	private String ion = "";
	
	public void setParams(String folder, String pdb, String chain, String ion, String homeFolder)
	{
		folderName = folder; 
		this.pdb = pdb;
		this.chain = chain;
		this.ion = ion;
		this.homeFolder = homeFolder;
	}
	
	public void runTools() throws Exception
	{
		
		File out = new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/thirdparty.log");
		System.out.println(out.getAbsolutePath());
		FileWriter fw = new FileWriter(out);
		BufferedWriter bw = new BufferedWriter(fw);
		
		
		/**
		 * run dssp
		 */
		Process dssp = Runtime.getRuntime().exec("/var/lib/tomcat7/webapps/ROOT/software/dssp/dssp -i /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + 
				".pdb -o /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + ".dssp");
		System.out.println("/var/lib/tomcat7/webapps/ROOT/software/dssp/dssp -i /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + 
				".pdb -o /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + ".dssp");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dssp.getErrorStream()));
		String str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		dssp.waitFor();
		
		
		/**
		 * psi-blast
		 */
		Process blast = Runtime.getRuntime().exec("/var/lib/tomcat7/webapps/ROOT/software/blast/psiblast -db /var/lib/tomcat7/webapps/ROOT/software/blast/database -query "
				+ "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + "_" + chain + ".fasta.txt -evalue 0.001 -num_iterations 2 -out_ascii_pssm " + 
				"/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + "_" + chain + ".fasta.pssm -out /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + ".psiblast");
		System.out.println("/var/lib/tomcat7/webapps/ROOT/software/blast/psiblast -db /var/lib/tomcat7/webapps/ROOT/software/blast/database -query "
				+ "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + "_" + chain + ".fasta.txt -evalue 0.001 -num_iterations 2 -out_ascii_pssm " + 
				"/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + "_" + chain + ".fasta.pssm -out /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + ".psiblast");
		bufferedReader = new BufferedReader(new InputStreamReader(blast.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		blast.waitFor();
		
		
		/**
		 * DISOPRED
		 */
		Process disopred = Runtime.getRuntime().exec("/var/lib/tomcat7/webapps/ROOT/software/DISOPRED/rundisopred /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + "_" + chain + ".fasta.txt", null, new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/"));
		System.out.println("/var/lib/tomcat7/webapps/ROOT/software/DISOPRED/rundisopred /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + "_" + chain + ".fasta.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(disopred.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		disopred.waitFor();
		
		
		/**
		 * NACCESS
		 */
		System.out.println("/bin/csh /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/naccess /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + ".pdb");
		Process naccess = Runtime.getRuntime().exec("/bin/csh /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/naccess /var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/" + pdb + ".pdb", null, new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/"));
		bufferedReader = new BufferedReader(new InputStreamReader(naccess.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		naccess.waitFor();
		
		
		/**
		 * hsexpo
		 */
		System.out.println("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t HSEBU -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/1apnad.txt" + " /var/lib/tomcat7/webapps/ROOT/users/" +
				 folderName + "/" + pdb + ".pdb");
		Process hsexpo1 = Runtime.getRuntime().exec("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t HSEBU -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/ex_" + pdb + "_HSEBU  /var/lib/tomcat7/webapps/ROOT/users/" +
		 folderName + "/" + pdb + ".pdb");
		
		bufferedReader = new BufferedReader(new InputStreamReader(hsexpo1.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		hsexpo1.waitFor();
		
		
		System.out.println("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t HSEBD -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/1apnad.txt" + " /var/lib/tomcat7/webapps/ROOT/users/" +
				 folderName + "/" + pdb + ".pdb");
		Process hsexpo2 = Runtime.getRuntime().exec("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t HSEBD -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/ex_" + pdb + "_HSEBD  /var/lib/tomcat7/webapps/ROOT/users/" +
		 folderName + "/" + pdb + ".pdb");
		
		bufferedReader = new BufferedReader(new InputStreamReader(hsexpo2.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		hsexpo2.waitFor();
		
		
		System.out.println("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t HSEAU -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/1apnad.txt" + " /var/lib/tomcat7/webapps/ROOT/users/" +
				 folderName + "/" + pdb + ".pdb");
		Process hsexpo3 = Runtime.getRuntime().exec("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t HSEAU -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/ex_" + pdb + "_HSEAU  /var/lib/tomcat7/webapps/ROOT/users/" +
		 folderName + "/" + pdb + ".pdb");
		
		bufferedReader = new BufferedReader(new InputStreamReader(hsexpo3.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		hsexpo3.waitFor();
		
		
		System.out.println("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t HSEAD -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/1apnad.txt" + " /var/lib/tomcat7/webapps/ROOT/users/" +
				 folderName + "/" + pdb + ".pdb");
		Process hsexpo4 = Runtime.getRuntime().exec("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t HSEAD -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/ex_" + pdb + "_HSEAD  /var/lib/tomcat7/webapps/ROOT/users/" +
		 folderName + "/" + pdb + ".pdb");
		
		bufferedReader = new BufferedReader(new InputStreamReader(hsexpo4.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		hsexpo4.waitFor();
		
		
		System.out.println("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t CN -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/1apnad.txt" + " /var/lib/tomcat7/webapps/ROOT/users/" +
				 folderName + "/" + pdb + ".pdb");
		Process hsexpo5 = Runtime.getRuntime().exec("python /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t CN -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/ex_" + pdb + "_CN  /var/lib/tomcat7/webapps/ROOT/users/" +
		 folderName + "/" + pdb + ".pdb");
		
		bufferedReader = new BufferedReader(new InputStreamReader(hsexpo5.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		hsexpo5.waitFor();
		
		
		String ev[] = {"PATH=$PATH:/usr/local/bin:/usr/bin"};
		System.out.println("/usr/bin/python2.7 /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t RD -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/1apnad.txt" + " /var/lib/tomcat7/webapps/ROOT/users/" +
				 folderName + "/" + pdb + ".pdb");
		Process hsexpo6 = Runtime.getRuntime().exec("/usr/bin/python2.7 /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t RD -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/ex_" + pdb + "_RD  /var/lib/tomcat7/webapps/ROOT/users/" +
		 folderName + "/" + pdb + ".pdb", ev, new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/"));
		
		bufferedReader = new BufferedReader(new InputStreamReader(hsexpo6.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		hsexpo6.waitFor();
		
		
		System.out.println("/usr/bin/python2.7 /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t RDa -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/1apnad.txt" + " /var/lib/tomcat7/webapps/ROOT/users/" +
				 folderName + "/" + pdb + ".pdb");
		Process hsexpo7 = Runtime.getRuntime().exec("/usr/bin/python2.7 /var/lib/tomcat7/webapps/ROOT/software/hsexpo/hsexpo -t RDa -o " + "/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/ex_" + pdb + "_RDa  /var/lib/tomcat7/webapps/ROOT/users/" +
		 folderName + "/" + pdb + ".pdb", ev, new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/"));
		
		bufferedReader = new BufferedReader(new InputStreamReader(hsexpo7.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			//System.out.println(str);
			bw.write(str + "\n");
			bw.flush();
		}
		hsexpo7.waitFor();
		
		
		/**
		 * network
		 */
		System.out.println("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/calc_network_parameter.sh " + pdb + ".pdb " + chain + " 6.5 1");
		Process net = Runtime.getRuntime().exec("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/calc_network_parameter.sh " + pdb + ".pdb " + chain + " 6.5 1", null, new File("/var/lib/tomcat7/webapps/ROOT/users/" + folderName + "/"));
		
		bufferedReader = new BufferedReader(new InputStreamReader(net.getErrorStream()));
		str = "";
		while ((str=bufferedReader.readLine()) != null) 
		{
			bw.write(str + "\n");
			bw.flush();
		}
		net.waitFor();
		
		
		bw.close();
		fw.close();
	}
}
