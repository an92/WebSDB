package Servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.SearchDBDomain;


/**
 * Servlet implementation class show
 */
@WebServlet("/show")
public class searchDBwithDomain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchDBwithDomain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String domain = request.getParameter("domain").trim();
		System.out.println(domain);
		//request.setAttribute("SearchID", type);
		SearchDBDomain obj = new SearchDBDomain();
		try
		{
			List list = obj.getResult(domain);
			request.setAttribute("data", list);
		}catch(Exception e)
		{
			
		}
        //request.setAttribute("result", obj.getNum());
        //response.sendRedirect("ShowResult.jsp");
        request.getRequestDispatcher("ShowResult.jsp").forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
	}

}
