package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.*;

/**
 * Servlet implementation class searchDBwithDisease
 */
@WebServlet("/searchDBwithDisease")
public class searchDBwithDisease extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchDBwithDisease() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String disease = request.getParameter("Disease").trim();
		
		SearchDBDisease obj = new SearchDBDisease();
		try
		{
			List list = obj.getResult(disease);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
