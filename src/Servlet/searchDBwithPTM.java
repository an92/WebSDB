package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Beans.SearchDBPTM;

/**
 * Servlet implementation class searchDBwithPTM
 */
@WebServlet("/searchDBwithPTM")
public class searchDBwithPTM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchDBwithPTM() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		String ptm = request.getParameter("PTM").trim();
		ptm = ptm.replaceAll(",", "#");
		SearchDBPTM obj = new SearchDBPTM();
		try
		{
			List list = obj.getResult(ptm);
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
