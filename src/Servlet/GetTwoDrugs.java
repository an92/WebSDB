package Servlet;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetTwoDrugs
 */
@WebServlet("/GetTwoDrugs")
public class GetTwoDrugs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String folderName = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTwoDrugs() {
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
		System.out.println("arrived here");
		String drugA = (String)request.getParameter("drugA");
		System.out.println(drugA);
		String drugB = (String)request.getParameter("drugB");
		System.out.println(drugB);
		
		long time = System.currentTimeMillis();
		Random ran = new Random(time);
		folderName = Long.toString(time)+Long.toString(ran.nextLong());
		File folder = new File("/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/");
		//System.out.println(folder.setWritable(true, false));
		
		if(!folder.exists())
		{
			if(folder.mkdir())
			{
				System.out.println(folder.getAbsolutePath());
			}
			else
			{
				System.out.println(folder.getAbsolutePath());
				System.out.println("can not make folder");
				System.exit(1);
			}
		}
		else
		{
			while(folder.exists())
			{
				time = System.currentTimeMillis();
				ran = new Random(time);
				folderName = Long.toString(time)+Long.toString(ran.nextLong());
				folder = new File("/var/lib/tomcat7/webapps/smolcom/" + folderName + "/"); 
				folder.setWritable(true, false);
			}
			if(folder.mkdir())
			{
				System.out.println(folder.getAbsolutePath());
			}
			else
			{
				System.out.println("can not make folder");
				System.exit(1);
			}
		}
		System.out.println(folderName);
		
		//copy required stuff to user folder
		try
		{
			Process process = Runtime.getRuntime().exec("cp /var/lib/tomcat7/webapps/smolcom/drugData.csv " +  "/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/");
			process.waitFor();
			Process process2 = Runtime.getRuntime().exec("cp /var/lib/tomcat7/webapps/smolcom/tanimoto.R " +  "/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/");
			process2.waitFor();
			Process process3 = Runtime.getRuntime().exec("cp /var/lib/tomcat7/webapps/smolcom/RF16.model " +  "/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/");
			process3.waitFor();
			Process process4 = Runtime.getRuntime().exec("cp /var/lib/tomcat7/webapps/smolcom/results.jsp " +  "/var/lib/tomcat7/webapps/smolcom/users/" + folderName + "/");
			process4.waitFor();
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/accepted.jsp");
		request.setAttribute("id", folderName);
		request.setAttribute("email", "");
		dispatcher.forward(request, response);
		
		
		//now we extract and form arff test file for these two drugs and run the model
		RunThreadDC run = new RunThreadDC();
		run.setDrug(drugA, drugB, folderName);
		//run.run();
		new Thread(run).start();
		
	}

}
