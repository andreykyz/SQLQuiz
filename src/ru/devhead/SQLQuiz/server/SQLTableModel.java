package ru.devhead.SQLQuiz.server;

//import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;
//import java.util.logging.StreamHandler;


public class SQLTableModel  {

	
	private static final long serialVersionUID = 1L;
	
	// table of data 
	Object [][] tableData;
	String[] columnNames; 
	
	//URL for the database connection
	String databaseURL;
	// Array of column names
	String query;
	// Driver name
	String driverName = "org.postgresql.Driver";
	String username = "test";
	String password = "123";
	
	Logger logger;
	
	
	SQLTableModel() {
		logger = Logger.getLogger("MyLog");
		logger.setLevel(Level.ALL);
		logger.info( "Create SQLTableModel");

		//defaultdata
		tableData = new String[][] {{"Результат запроса"} };
		columnNames = new String[] {" "};
//		fireTableDataChanged();
	}
	
	
	public int getColumnCount() {
		
		return columnNames == null ? 0 : columnNames.length;
	}

	public String getColumnName(int nCol) {

		return columnNames == null ? "unknow" : columnNames[nCol];
	}
	
	
	public int getRowCount() {
		
		return tableData == null ? 0 : tableData.length;
	}
	
	

	
	public Object getValueAt(int nRow, int nCol) {
	
		return tableData[nRow][nCol];
	}
	
	public boolean isCellEditable(int nRow, int nCol) {
		
	    return false;
	  }

	void loadTableData() {

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
				logger.info(rsmd.getColumnName(i));
				// [i-1] because array indexes start from 0
				columnNames[i - 1] = rsmd.getColumnName(i);
				logger.info(columnNames[i - 1]);
			}
			// create new array of table data
			rs.last();
			int numberOfRow = rs.getRow();
			tableData = new String[numberOfRow][numberOfColumns];
			rs.beforeFirst();
			logger.info("numberOfRow = " + numberOfRow);
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

	void executeQuery(String databaseURL, String query) {
		
		this.databaseURL = databaseURL;
		this.query = query;
		loadTableData();
	}

}
