package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JDBCConnectionUtil {

	//JDBC - Java Database Connectivity
	//DB - Database
	
	public static Connection getConnection() throws SQLException{
		String url="jdbc:oracle:thin:@iandb.cbdnm6ikincc.us-east-2.rds.amazonaws.com:1521:ORCL";
		String user="ianszcze";
		String pass="Cabbage8$";
		return DriverManager.getConnection(url, user, pass);
	}
}
