package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static String DB_HOST = "localhost";
	private static int DB_PORT = 3306;
	private static String DB_NAME = "qlhp";
	private static String DB_USERNAME = "root";
	private static String DB_PASSWORD = "";

	public static Connection getConnection() {
		String uri = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(uri, DB_USERNAME, DB_PASSWORD);
			System.out.println("Connect success!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connect failed!");
		}

		return conn;
	}

}