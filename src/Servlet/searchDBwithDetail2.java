package Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import BeansForDetailSearch.*;

/**
 * Servlet implementation class searchDBDetail
 */
@WebServlet("/searchDBDetail")
public class searchDBwithDetail2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchDBwithDetail2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		String DBid = request.getParameter("dbid").trim();
		int ProteinID = Integer.parseInt(DBid.substring(2));
		System.out.println(ProteinID);

		SearchDetailGeneralInfo obj1 = new SearchDetailGeneralInfo(); 
		SearchDetailPDB obj2 = new SearchDetailPDB();
		SearchDetailDisorderPrediction obj3 = new SearchDetailDisorderPrediction();
		SearchDetailInteraction obj4 = new SearchDetailInteraction();
		SearchDetailPTM obj5 = new SearchDetailPTM();
		SearchDetailPfam obj6 = new SearchDetailPfam();
		SearchDetailPathway obj7 = new SearchDetailPathway();
		SearchDetailMutation obj8 = new SearchDetailMutation();
		
		try
		{
			List list = obj1.getResult(DBid);
			request.setAttribute("general", list);
			
			List list2 = obj2.getResult(ProteinID);
			request.setAttribute("PDB", list2);
			
			List list3 = obj3.getResult(ProteinID);
			request.setAttribute("DisorderPrediction", list3);
			
			List list4 = obj4.getResult(ProteinID);
			request.setAttribute("Interaction", list4);
			
			List list5 = obj5.getResult(ProteinID);
			request.setAttribute("PTM", list5);
			
			List list6 = obj6.getResult(ProteinID);
			request.setAttribute("Pfam", list6);
			
			List list7 = obj7.getResult(ProteinID);
			request.setAttribute("Pathway", list7);
			
			List list8 = obj8.getResult(ProteinID);
			request.setAttribute("Mutation", list8);
			
			
		}catch(Exception e)
		{
			
		}
		request.getRequestDispatcher("ShowDetail2.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
