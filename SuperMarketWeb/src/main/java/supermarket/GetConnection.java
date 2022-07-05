package supermarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
	static Connection conn;
	static Connection getConnection(String username,String password) {
		if(conn == null) {
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smarket",username,password);
				return con;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}  
		}
	
		return conn;
	}
	
}
