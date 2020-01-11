package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DButil {

	//本地服务器
	private static String url = "jdbc:mysql:///orderfoods";
	private static String user = "root";
	private static String psw = "mysql123";
	
	//阿里云服务器
//	private static String url = "jdbc:mysql://localhost/orderfoods?useSSL=false";
//	private static String user = "hs";
//	private static String psw = "mysql123";

	//google服务器
//	private static String url = "jdbc:mysql://localhost/orderfoods?useSSL=false";
//	private static String user = "user1";
//	private static String psw = "MySQL_user1";
	
	
	// 获得数据库连接
	static {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, psw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
