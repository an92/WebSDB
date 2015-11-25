package Beans;

import java.io.*;
import java.util.HashMap;

public class generateInput 
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
	
	public void generateFile() throws Exception
	{
		// TODO Auto-generated method stub
		
				System.out.println("generating input.....");
		
				HashMap<String, String> hMap1 = new HashMap<String, String>();
				
				hMap1.put("GLY", "G");
			    hMap1.put("ALA", "A");
			    hMap1.put("LEU", "L");
			    hMap1.put("ILE", "I");
			    hMap1.put("VAL", "V");
			    hMap1.put("PRO", "P");
			    hMap1.put("PHE", "F");
			    hMap1.put("MET", "M");
			    hMap1.put("SER", "S");
			    hMap1.put("GLN", "Q");
			    hMap1.put("THR", "T");
			    hMap1.put("CYS", "C");
			    hMap1.put("ASN", "N");
			    hMap1.put("TYR", "Y");
			    hMap1.put("TRP", "W");
			    hMap1.put("ASP", "D");
			    hMap1.put("GLU", "E");
			    hMap1.put("LYS", "K");
			    hMap1.put("ARG", "R");
			    hMap1.put("HIS", "H");
				
			    HashMap<String, String> hMap2 = new HashMap<String, String>();
				
				hMap2.put("G","GLY");
			    hMap2.put("A","ALA");
			    hMap2.put("L","LEU");
			    hMap2.put("I","ILE");
			    hMap2.put("V","VAL");
			    hMap2.put("P","PRO");
			    hMap2.put("F","PHE");
			    hMap2.put("M","MET");
			    hMap2.put("S","SER");
			    hMap2.put("Q","GLN");
			    hMap2.put("T","THR");
			    hMap2.put("C","CYS");
			    hMap2.put("N","ASN");
			    hMap2.put("Y","TYR");
			    hMap2.put("W","TRP");
			    hMap2.put("D","ASP");
			    hMap2.put("E","GLU");
			    hMap2.put("K","LYS");
			    hMap2.put("R","ARG");
			    hMap2.put("H","HIS");
			    
			    
			    
			    
				File file = new File(homeFolder + "users/" + folderName + "/" + pdb + ".pdb");
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				String str = br.readLine();
				HashMap<Integer, String> hMap3 = new HashMap<Integer, String>();
				String seqPDB = "";
				int count = -1;
				while(str!=null)
				{
					str = str.trim();
					if(str.startsWith("ATOM") && str.substring(21, 22).equals(chain) && str.substring(12, 16).trim().equals("CA") && str.substring(16,20).trim().length()==3)
					{
						count++;
						System.out.println(str);
						seqPDB = seqPDB + hMap1.get(str.substring(17, 20).trim());
						hMap3.put(count, str.substring(30, 38).trim() + "==="+str.substring(38, 46).trim() + "===" + str.substring(46, 54).trim() + "===" + str.substring(22, 26).trim());
					}
					str = br.readLine();
				}
				
				System.out.println(seqPDB);
				br.close();
				fr.close();
				
				File fasta = new File(homeFolder + "users/" + folderName + "/" + pdb + "_" + chain + ".fasta.txt");
				FileReader fin = new FileReader(fasta);
				BufferedReader bin = new BufferedReader(fin);
				
				String line = bin.readLine();
				String seqFasta = "";
				while(line!=null)
				{
					line = line.trim();
					System.out.println(line);
					if(!line.startsWith(">"))
						seqFasta = seqFasta + line;
					line = bin.readLine();
				}
				System.out.println(seqFasta);
				bin.close();
				fin.close();
				
				File out = new File(homeFolder + "users/" + folderName + "/alignInput.fasta");
				FileWriter fw = new FileWriter(out);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(">PDBseq\n" + seqPDB + "\n>Fastaseq\n" + seqFasta);
				bw.flush();
				bw.close();
				fw.close();
				
				Process process2 = Runtime.getRuntime().exec(homeFolder + "users/" + folderName + "/clustalw2 -INFILE=" + out.getAbsolutePath() + " -TYPE=PROTEIN -OUTFILE=" + homeFolder + "users/" + folderName + "/alignment.align"); 
				
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process2.getInputStream()));
				String useless = "";
				while ( (useless=bufferedReader.readLine()) != null) 
				{}
				process2.waitFor();
				
				//read alignment file and determine the positions of aa in pdb sequence and fasta sequence
				File align = new File(homeFolder  + "users/" +  folderName + "/alignment.align");
				FileReader fa = new FileReader(align);
				BufferedReader ba = new BufferedReader(fa);
				String info = ba.readLine();
				String query = "";
				String subject = "";
				String alignment = "";
				int counter = 0;
				while(info != null)
				{

					counter++;

					if(!info.equals("CLUSTAL 2.1 multiple sequence alignment") && info.length() > 16)
					{
						if(counter >=4 && (counter)%4 == 0)
						{
							query = query + info.substring(16);
						}
						if(counter >=5 && (counter)%4 == 1)
						{
							subject = subject + info.substring(16);
						}
						if(counter >=6 && counter%4 == 2)
						{
							alignment = alignment + info.substring(16);
						}
						
					}
					info = ba.readLine();
						
				}
				ba.close();
				fa.close();
				
				System.out.println(query);
				System.out.println(subject);
				
				
				//determine the positions
				//int positionPDB = 0;
				int positionSeq = 0;
				
				File output = new File(homeFolder + "users/" + folderName + "/predict_PDB_SEQ");
				FileWriter fwo = new FileWriter(output);
				BufferedWriter bwo = new BufferedWriter(fwo);
				
				int flag = -1;
				for(int i = 0 ; i < query.length(); i++)
				{
					//if(!query.substring(i, i+1).equals("-"))
					//	positionPDB ++;
					if(!subject.substring(i, i+1).equals("-"))
						positionSeq ++;
					if(!query.substring(i, i+1).equals("-"))
						flag++;
					if(!query.substring(i, i+1).equals("-") && query.substring(i, i+1).equals(subject.substring(i, i+1)))
					{
						//flag++;
						bwo.write(ion + " " + pdb +  " " + chain + " " + hMap2.get(query.substring(i, i+1)) + " " + hMap3.get(flag).split("===")[3].trim() + " " + hMap3.get(flag).split("===")[0].trim() + " " + hMap3.get(flag).split("===")[1].trim() + " " + hMap3.get(flag).split("===")[2].trim() + " " + positionSeq + "\n");
						bwo.flush();
					}
				}
				
				bwo.close();
				fwo.close();
	}
}
