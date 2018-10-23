package com.currencyfair.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class used to make sure that every tool is closed at the end of each.
 * transaction
 * 
 * 
 *
 */
public class DBUtil {

	/**
	 * Method used to close the database connection Object.
	 * 
	 * @param connection
	 *            the Connection itself
	 */
	public static void close(Connection connection) {
		
		if (connection != null) {
			
			try {
				
				connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}
		}
	}

	/**
	 * Method used to flag as finished using the statement Object.
	 * 
	 * @param statement
	 *            the Statement Object
	 */
	public static void close(Statement statement) {
		
		if (statement != null) {
			
			try {
				
				statement.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}
		}
	}

	/**
	 * Method used to flag as finished using the preparedStatement Object.
	 * 
	 * @param preparedStatement
	 *            the PreparedStatement Object
	 */
	public static void close(PreparedStatement preparedStatement) {
		
		if (preparedStatement != null) {
			
			try {
				
				preparedStatement.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}
		}
	}

	/**
	 * Method used to flag as finished using the resultSet Object.
	 * 
	 * @param resultSet
	 *            the ResultSet Object
	 */
	public static void close(ResultSet resultSet) {
		
		if (resultSet != null) {
			
			try {
				
				resultSet.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}
		}
	}

}
