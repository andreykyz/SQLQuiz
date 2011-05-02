package ru.devhead.SQLQuiz.server;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MainWorker extends HttpServlet {
	
	//URL for the database connection
	String databaseURL = "url";
	// Array of column names
	String driverName = "org.postgresql.Driver";
	String username = "test";
	String password = "123";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {

		    PrintWriter out = resp.getWriter();
		    out.println('[');
		    String requestId = req.getParameter("requestid");
		    String query = req.getParameter("query");
//		    out.println("callback"+requesid +""({datatable:[["f","d","g"],["ads","fsdf","fsdf"],["5345345","45345345","534534"]]}); 
		    out.println(']');
		    out.flush();
	}

	void loadTableData(String query) {
		Object [][] tableData;
		System.out.println(driverName);
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection(databaseURL,
					username, password);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			// create new array
			columnNames = new String[numberOfColumns];
			// get the column names; column indexes start from 1
			for (int i = 1; i < numberOfColumns + 1; i++) {
//				logger.info(rsmd.getColumnName(i));
				// [i-1] because array indexes start from 0
				columnNames[i - 1] = rsmd.getColumnName(i);
//				logger.info(columnNames[i - 1]);
			}
			// create new array of table data
			rs.last();
			int numberOfRow = rs.getRow();
			tableData = new String[numberOfRow][numberOfColumns];
			rs.beforeFirst();
//			logger.info("numberOfRow = " + numberOfRow);
			// get the table data
			// row loop
			for (int i = 1; i < numberOfRow + 1; i++) {
				logger.info("Start get table data");
				if (!rs.next())
					break;
				for (int j = 1; j < numberOfColumns + 1; j++) {
					logger.info(rs.getString(j));
					tableData[i - 1][j - 1] = rs.getString(j);
					logger.info((String) tableData[i - 1][j - 1]);
				}
			}

		} catch (SQLFeatureNotSupportedException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
