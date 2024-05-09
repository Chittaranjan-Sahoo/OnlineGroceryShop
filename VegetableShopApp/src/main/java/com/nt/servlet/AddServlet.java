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
@WebServlet("/addurl")
public class AddServlet extends HttpServlet {
	private static final String INSERT_QUERY="INSERT INTO VEGE_TAB VALUES(VEG_SEQ.NEXTVAL,?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		resp.setHeader("refresh","10");
		
		String name=req.getParameter("vname");
		String price=req.getParameter("vprice");
		String qty=req.getParameter("vqty");
		try {
			getClass().forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
		
		PrintWriter pw=resp.getWriter();
		try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
				PreparedStatement ps=con.prepareStatement(INSERT_QUERY);)
		
		{
			ps.setString(1, name);
			ps.setString(2, price);
			ps.setString(3, qty);
			int count=ps.executeUpdate();
			if(count!=0)
			{
				pw.println("<h1 style='color:green;text-align:center'>Data Inserted Sucessfuly</h1>");
			}
			else
			{
				pw.println("<h1 style='color:red;text-align:center'>Data Not Inserted</h1>");
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
		doGet(req, resp);;
	}

}
