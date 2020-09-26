package com.myclass.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
	
	private static String url = "jdbc:mysql://localhost:3306/crm";
	private static String username = "root";
	private static String password = "minhthuan";

	public static Connection getConnection() {
		
		// Su dung lop Driver cua JDBC
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url, username, password);
					System.out.println("Success");
					return connection;
					
				} catch (ClassNotFoundException e) {
					System.out.println("Khong tim thay Driver!");
				} catch (SQLException e) {
					System.out.println("Khong tim thay database!");
				}
		
		return null;
	}
}
