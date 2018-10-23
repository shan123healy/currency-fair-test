package com.currencyfair.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class used to manage the database connection.
 * 
 * 
 *
 */
public class ConnectionFactory {

	// static reference to itself
	private static ConnectionFactory instance = new ConnectionFactory();

	Properties configFile = new Properties();

	// private constructor
	private ConnectionFactory() {
	}

	private Connection createConnection() {

		Connection connection = null;

		try {

			configFile.load(ConnectionFactory.class.getClassLoader()
					.getResourceAsStream("config.properties"));
			
			Class.forName(configFile.getProperty("database.driverClassName"));

			connection = DriverManager.getConnection(
					configFile.getProperty("database.url"),
					configFile.getProperty("database.username"),
					configFile.getProperty("database.password"));

		} catch (SQLException e) {

			System.out.println("ERROR: Unable to Connect to Database.");

		} catch (IOException e) {

			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}

		return connection;
	}

	public static Connection getConnection() {
		return instance.createConnection();
	}
}
