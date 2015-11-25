package Servlet;

import Beans.changeResult;
import Beans.drawPic;
import Beans.emailToUser;
import Beans.getResults;
import Beans.runModel;
import Beans.GenerateThirdPartyInputs;

public class runThread implements Runnable
{
	private String folderName = "";
	private String path = "";
	private String email = "";
	private String ion = "";
	private String pdb = "";
	private String chain = "";
	private String homeFolder = "/var/lib/tomcat7/webapps/ROOT/";
	
	public void setFolder(String name, String path, String email, String ion, String pdb, String chain, String homeFolder)
    {
        this.folderName = name;
        this.path = path;
        this.email = email;
        this.ion = ion;
        this.pdb = pdb;
        this.chain = chain;
        this.homeFolder = homeFolder;
    }
	
	public void run()
	{ 
		try
		{
				//first we need to generate the input files by ourselves
				GenerateThirdPartyInputs gen = new GenerateThirdPartyInputs();
				gen.setParams(folderName, pdb, chain, ion, homeFolder);
				gen.runTools();
			
			   runModel run = new runModel();
			   run.setParams(folderName, pdb, chain, ion, homeFolder);
			   run.runProgram();
	
			   System.out.println("=======================");
			   changeResult change = new changeResult();
			   change.setParams(path, ion);
			   change.change();
			   
			   getResults obj = new getResults();
			   obj.setParams(path, ion);
			   String presults = obj.getResult();
			   
			   
			   
			   drawPic draw = new drawPic();
			   draw.setParams(path, ion);
			   draw.draw();
			   
			   System.out.println(path);
			   System.out.println(presults);
			   
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
				
				threshold = Math.abs(threshold) + 0.5;
				
				String info = "Amino acid residues with a score larger than the prediction threshold are predicted as metal-binding sites, while those with the score lower than the prediction threshold are predicted as non-binding sites. The higher a positive score of a residue, the more likely such residue is a metal-binding site. The threshold for " + ion + " is: " + threshold; 
			   
			    String results = "Dear User,\n\n Your job " + folderName + " has successfully finished. You can visit http://metalexplorer.erc.monash.edu.au/users/" + folderName + "/results.jsp for detailed information. \n\n" + info + "\n\n The prediction results for " + ion + " are as follows: \n\nPosition\t\t\tPrediction Score\n\n" + presults;
			   
			   System.out.println(results);
			   
			   
			   
			   try
			   {
				   System.out.println("Emailing...");
				   emailToUser emails = new emailToUser();
				   emails.setParams(email, results, folderName);
				   emails.sendEmail();
			   }catch(Exception e)
			   {
				   
			   }
	   
	   }catch(Exception e)
	   {
		   
	   }
	}
}
