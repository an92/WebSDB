package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.SearchDBKinase;

/**
 * Servlet implementation class searchDBwithKinase
 */
@WebServlet("/searchDBwithKinase")
public class searchDBwithKinase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchDBwithKinase() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
		String kinase = request.getParameter("kinase").trim().toUpperCase();
		System.out.println(kinase);
		SearchDBKinase obj = new SearchDBKinase();
		try
		{
			List list = obj.getResult(kinase);
			
			System.out.println(list.size());
			request.setAttribute("data", list);
		}catch(Exception e)
		{
			
		}
        request.getRequestDispatcher("ShowResult.jsp").forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
