package Servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class newUpload
 */
@WebServlet("/newUpload")
public class newUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public newUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		try{
			
			long time = System.currentTimeMillis();
			Random ran = new Random(time);
			String filename = Long.toString(time)+Long.toString(ran.nextLong());
			
			File out = new File("/var/lib/tomcat7/webapps/polyq2/newLog/" + filename + ".txt");
			System.out.println(out.getAbsolutePath());
			FileWriter fw = new FileWriter(out);
			BufferedWriter bw = new BufferedWriter(fw);
			
			String surname = request.getParameter("surname").trim();
			System.out.println(surname);
			bw.write("surname: " + surname+ "\n");
			bw.flush();
			String givenname = request.getParameter("givenname").trim();
			System.out.println(givenname);
			bw.write("givenname: " + givenname+ "\n");
			bw.flush();
			String email = request.getParameter("email").trim();
			System.out.println(email);
			bw.write("email: " + email+ "\n");
			bw.flush();
			
			String proteinname = request.getParameter("proteinname").trim();
			System.out.println(proteinname);
			bw.write("proteinname: " + proteinname+ "\n");
			bw.flush();
			String species = request.getParameter("species").trim();
			System.out.println(species);
			bw.write("species: " + species+ "\n");
			bw.flush();
			String genename = request.getParameter("genename").trim();
			System.out.println(genename);
			bw.write("genename: " + genename+ "\n");
			bw.flush();
			String uniprotid = request.getParameter("uniprotid").trim();
			System.out.println(uniprotid);
			bw.write("uniprotid: " + uniprotid+ "\n");
			bw.flush();
			String molecularweight = request.getParameter("molecularweight").trim();
			System.out.println(molecularweight);
			bw.write("molecularweight: " + molecularweight+ "\n");
			bw.flush();
			String proteinsequence = request.getParameter("proteinsequence").trim();
			System.out.println(proteinsequence);
			bw.write("proteinsequence: " + proteinsequence+ "\n");
			bw.flush();
			String proteinfunction = request.getParameter("proteinfunction").trim();
			System.out.println(proteinfunction);
			bw.write("proteinfunction: " + proteinfunction+ "\n");
			bw.flush();
			String localization = request.getParameter("localization").trim();
			System.out.println(localization);
			bw.write("localization: " + localization+ "\n");
			bw.flush();
					
			String pdbid = request.getParameter("pdbid").trim();
			System.out.println(pdbid);
			bw.write("pdbid: " + pdbid+ "\n");
			bw.flush();
//			String method = request.getParameter("method").trim();
//			System.out.println(method);
//			bw.write("method: " + method+ "\n");
//			bw.flush();
//			String resolution = request.getParameter("resolution").trim();
//			System.out.println(resolution);
//			bw.write("resolution: " + resolution+ "\n");
//			bw.flush();
//			String chain = request.getParameter("chain").trim();
//			System.out.println(chain);
//			bw.write("chain: " + chain+ "\n");
//			bw.flush();
			
			String partnername = request.getParameter("partnername").trim();
			System.out.println(partnername);
			bw.write("partnername: " + partnername+ "\n");
			bw.flush();
//			String partneruniid = request.getParameter("partneruniid").trim();
//			System.out.println(partneruniid);
//			bw.write("partneruniid: " + partneruniid+ "\n");
//			bw.flush();
//			String partnermethod = request.getParameter("partnermethod").trim();
//			System.out.println(partnermethod);
//			bw.write("partnermethod: " + partnermethod+ "\n");
//			bw.flush();
//			String partnerpubmed = request.getParameter("partnerpubmed").trim();
//			System.out.println(partnerpubmed);
//			bw.write("partnerpubmed: " + partnerpubmed+ "\n");
//			bw.flush();
			
			String mutationposition = request.getParameter("mutationposition").trim();
			System.out.println(mutationposition);
			bw.write("mutationposition: " + mutationposition+ "\n");
			bw.flush();
//			String wildtypeaa = request.getParameter("wildtypeaa").trim();
//			System.out.println(wildtypeaa);
//			bw.write("wildtypeaa: " + wildtypeaa+ "\n");
//			bw.flush();
//			String mutantaa = request.getParameter("mutantaa").trim();
//			System.out.println(mutantaa);
//			bw.write("mutantaa: " + mutantaa+ "\n");
//			bw.flush();
//			String disease = request.getParameter("disease").trim();
//			System.out.println(disease);
//			bw.write("disease: " + disease+ "\n");
//			bw.flush();
//			String mutationpubmed = request.getParameter("mutationpubmed").trim();
//			System.out.println(mutationpubmed);
//			bw.write("mutationpubmed: " + mutationpubmed+ "\n");
//			bw.flush();
			
			String ptmposition = request.getParameter("ptmposition").trim();
			System.out.println(ptmposition);
			bw.write("ptmposition: " + ptmposition+ "\n");
			bw.flush();
//			String ptmaa = request.getParameter("ptmaa").trim();
//			System.out.println(ptmaa);
//			bw.write("ptmaa: " + ptmaa+ "\n");
//			bw.flush();
//			String ptmtype = request.getParameter("ptmtype").trim();
//			System.out.println(ptmtype);
//			bw.write("ptmtype: " + ptmtype+ "\n");
//			bw.flush();
//			String ptmkinasename = request.getParameter("ptmkinasename").trim();
//			System.out.println(ptmkinasename);
//			bw.write("ptmkinasename: " + ptmkinasename+ "\n");
//			bw.flush();
//			String ptmpubmed = request.getParameter("ptmpubmed").trim();
//			System.out.println(ptmpubmed);
//			bw.write("ptmpubmed: " + ptmpubmed+ "\n");
//			bw.flush();
			
			String fdstart = request.getParameter("fdstart").trim();
			System.out.println(fdstart);
			bw.write("fdstart: " + fdstart+ "\n");
			bw.flush();
//			String fdend = request.getParameter("fdend").trim();
//			System.out.println(fdend);
//			bw.write("fdend: " + fdend+ "\n");
//			bw.flush();
//			String fdfunction = request.getParameter("fdfunction").trim();
//			System.out.println(fdfunction);
//			bw.write("fdfunction: " + fdfunction+ "\n");
//			bw.flush();
//			String fdpubmed = request.getParameter("fdpubmed").trim();
//			System.out.println(fdpubmed);
//			bw.write("fdpubmed: " + fdpubmed+ "\n");
//			bw.flush();
			
			String mpdesciption = request.getParameter("mpdesciption").trim();
			System.out.println(mpdesciption);
			bw.write("mpdesciption: " + mpdesciption+ "\n");
			bw.flush();
//			String mpkegg = request.getParameter("mpkegg").trim();
//			System.out.println(mpkegg);
//			bw.write("mpkegg: " + mpkegg+ "\n");
//			bw.flush();
//			String mppubmed = request.getParameter("mppubmed").trim();
//			System.out.println(mppubmed);
//			bw.write("mppubmed: " + mppubmed+ "\n");
//			bw.flush();
				
			request.setAttribute("data", "successful");
			
			
			bw.close();
			fw.close();
		
		}catch(IOException e)
		{

			e.printStackTrace();
			request.setAttribute("data", "failed");
		}
		
		
		request.getRequestDispatcher("tmp2.jsp").forward(request, response);
	}

}
