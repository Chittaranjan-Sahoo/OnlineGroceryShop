package com.nt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/adminurl")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       



private static final String SELECT_QUERY="SELECT * FROM ADMIN1 WHERE UNAME=? AND PWD=?";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		response.setContentType("text/html");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try ( Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
				PreparedStatement ps=con.prepareStatement(SELECT_QUERY);
				)
		{
			String name=request.getParameter("uname");
			String pwd=request.getParameter("pwd");
			ps.setString(1, name);
			ps.setString(2, pwd);
			 ResultSet rs=ps.executeQuery();
			 if(rs.next())
			 {
				 RequestDispatcher rd=request.getRequestDispatcher("/index.html");
				 rd.forward(request, response);
			 }
			 else
			 {
				 RequestDispatcher rd=request.getRequestDispatcher("/error.jsp");
				 rd.forward(request, response);
			 }
			  
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}