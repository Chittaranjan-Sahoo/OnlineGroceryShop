package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/dropurl")
public class DropServlet extends HttpServlet {
	private static final String DELETE_QUERY="DELETE FROM GROC_TAB WHERE GROC_ID=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		PrintWriter pw=resp.getWriter();
		try {
			getClass().forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
		String id=req.getParameter("gid");
		try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
				PreparedStatement ps=con.prepareStatement(DELETE_QUERY);)
		
		{
			ps.setString(1, id);
			int count=ps.executeUpdate();
			if(count!=0)
			{
				RequestDispatcher rd=req.getRequestDispatcher("/drop.jsp");
				rd.include(req, resp);
			}
			else
			{
				RequestDispatcher rd=req.getRequestDispatcher("/error.jsp");
				rd.include(req, resp);
			}
		
		}
		catch(SQLException se)
		{
			RequestDispatcher rd=req.getRequestDispatcher("/error.jsp");
			rd.include(req, resp);
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		pw.println("<p  style='display:flex; justify-content:center; align-items:center; margin-top:300px;'><button><a href='index.html'>Go Back</a></button></p> ");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
