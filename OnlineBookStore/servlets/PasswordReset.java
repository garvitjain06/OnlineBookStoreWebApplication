package servlets;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import constants.IOnlineBookStoreConstants;
import sql.IUserContants;

import java.io.*;
import java.sql.*;

public class PasswordReset extends GenericServlet {
	
	private static final long serialVersionUID = 1L;

	public void service(ServletRequest req, ServletResponse res) throws IOException, ServletException {
		PrintWriter pw = res.getWriter();
		res.setContentType(IOnlineBookStoreConstants.CONTENT_TYPE_TEXT_HTML);
		String uName = req.getParameter(IUserContants.COLUMN_USERNAME);
		String npWord = req.getParameter(IUserContants.COLUMN_NEWPASSWORD);
		String cpWord = req.getParameter(IUserContants.COLUMN_CONFPASSWORD);
	   if(npWord.equals(cpWord) && npWord.length()>=8) {
		try {
			Connection con = DBConnection.getCon();
			PreparedStatement ps = con.prepareStatement("update " + IUserContants.TABLE_USERS + " set "
					+ IUserContants.COLUMN_PASSWORD + "=? where " + IUserContants.COLUMN_USERNAME +  "=?");
			ps.setString(1, npWord);
			ps.setString(2, uName);
			int rs = ps.executeUpdate();
			if(rs==1) {
				RequestDispatcher rd = req.getRequestDispatcher("Sample.html");		
				rd.include(req, res);
				pw.println("<div class=\"tab\"><h2>PASSWORD IS CHANGED SUCCESSFULLY....!</h2></div>");
				
				} 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	else
	{   RequestDispatcher rd = req.getRequestDispatcher("Sample.html");		
	    rd.include(req, res);
		pw.println("<div class=\"tab\"><h2>Password Mismatch Try Again......!</h2></div>");
	}
	
	}
}
