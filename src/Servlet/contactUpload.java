package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
/**
 * Servlet implementation class contactUpload
 */
@WebServlet("/contactUpload")
public class contactUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public contactUpload() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		String name = request.getParameter("name").trim();
		System.out.println(name);
		String email = request.getParameter("email").trim();
		System.out.println(email);
		String title = request.getParameter("title").trim();
		System.out.println(title);
		String content = request.getParameter("contentArea").trim();
		System.out.println(content);
		
		try{
		
		long time = System.currentTimeMillis();
		Random ran = new Random(time);
		String filename = Long.toString(time)+Long.toString(ran.nextLong());
		
		File out = new File("/var/lib/tomcat7/webapps/kinetochoreDB/contactLog/" + filename + ".txt");
		System.out.println(out.getAbsolutePath());
		FileWriter fw = new FileWriter(out);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("name: " + name + "\n");
		bw.flush();
		bw.write("email: " + email + "\n");
		bw.flush();
		bw.write("title: " + title + "\n");
		bw.flush();
		bw.write("content: " + content + "\n");
		bw.flush();
		request.setAttribute("data", "successful");
		
		
		bw.close();
		fw.close();
		
		}catch(IOException e)
		{

			e.printStackTrace();
			request.setAttribute("data", "failed");
		}
		
		
		request.getRequestDispatcher("tmp.jsp").forward(request, response);
		
	}

}
