package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/dropurl")
public class DropServlet extends HttpServlet {
	private static final String DELETE_QUERY="DELETE FROM VEGE_TAB WHERE VEG_ID=?";
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
		String id=req.getParameter("vid");
		try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
				PreparedStatement ps=con.prepareStatement(DELETE_QUERY);)
		
		{
			ps.setString(1, id);
			int count=ps.executeUpdate();
			if(count!=0)
			{
				pw.println("<h1 style='color:green;text-align:center'>Data Deleted Sucessfuly</h1>");
			}
			else
			{
				pw.println("<h1 style='color:red;text-align:center'>Data Not Deleted</h1>");
			}
		
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		pw.println("<p style='text-align:center; font-size=20px;'><a href='index.html'>Home</a></p>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
