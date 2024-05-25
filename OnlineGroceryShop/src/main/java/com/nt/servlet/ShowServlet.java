package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.processing.SupportedOptions;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/showurl")
public class ShowServlet extends HttpServlet {
	private static final String SELECT_QUERY="SELECT GROC_ID,GROC_NAME,GROC_PRICE,GROC_QTY FROM GROC_TAB";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		
		try {
			getClass().forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
		
		PrintWriter pw=resp.getWriter();
		try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
				PreparedStatement ps=con.prepareStatement(SELECT_QUERY);)
		
		{
			try(ResultSet rs=ps.executeQuery())
			{
			if(rs!=null)
			{
				pw.println("<body style='background-color:#62cf9c'>");
				pw.println("<table align='center' border='1' bgcolor='cyan' height='300px' width='600px' style= 'margin-top:200px'>");
				pw.println("<tr style='color:blue;text-align:center; font-weight:bold; font-size:30px'><td>Id</td><td>Nmae</td><td>Price</td><td>Qty</td></tr>");
				while(rs.next())
				{
					pw.println("<tr style='color:yellow;text-align:center; font-weight:bold; font-size:25px'><td>"+rs.getString(1)+"</td><td> "+rs.getString(2)+" </td><td>"+rs.getString(3)+" </td><td>"+rs.getString(4)+"</td>");
				}
				pw.println("</table>");
			}
			
			else
			{
				RequestDispatcher rd=req.getRequestDispatcher("/error.jsp");
				rd.forward(req, resp);
			}
			}	
		}
		catch(SQLException se)
		{
			RequestDispatcher rd=req.getRequestDispatcher("/error.jsp");
			rd.forward(req, resp);
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		pw.println("<p  style='display:flex; justify-content:center; align-items:center; margin-top:100px;'><button><a href='index.html'>Go Back</a></button></p> ");
		pw.println("</body>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}