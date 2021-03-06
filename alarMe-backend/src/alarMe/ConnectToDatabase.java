
package alarMe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ConnectToDatabase {
		
	protected Connection connection;
	private String db = "sql11166748";
	protected static String username;
	protected static String user_password;
	protected static int student_id;

	//method to connect to the database.
	public void setConnection() throws ClassNotFoundException, SQLException{
		try {
        	Class.forName("com.mysql.jdbc.Driver");
        	connection = null;
            String server = "sql11.freemysqlhosting.net";
            String database_username = "sql11166748";
            String database_password = "fPgJk4eNB2";
            String connectionString = "jdbc:mysql://" + server + "/" + db + "?user=" + database_username + "&password=" + database_password;
            connection = DriverManager.getConnection(connectionString);
            
        } catch (Exception e){
            e.printStackTrace();}
  }

	//method that checks if it has been added a new user to the database.
	public boolean checkForNewUser(){
		try {
			try {
				setConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Statement stm = null;
		try{
			int rows_database_old = 0;
			stm = connection.createStatement();
			ResultSet result = stm.executeQuery("select count(student_id) from Student"); 
	        result.next();
	        int rows_database = result.getInt(1); 
			//siden vi tenker at det bare blir lagt til �n bruker om gangen
			if(rows_database != 0 && rows_database > rows_database_old){
				return true;
			}else{
			return false;
			}
			
		}catch(Exception e){
			System.out.println("Her skjedde det noe feil: " + e);
					

	}
	return false;
			
}

	//method that returns the new user as a list [username, password].
	public ArrayList<String> newUser(){
		ArrayList<String> user = new ArrayList<String>();
		if (checkForNewUser() == true){
			Statement stm = null;
			try{
				int student_id = 9;
				stm = connection.createStatement();
				ResultSet rs = stm.executeQuery("select * from Student");
				while(rs.next()){
					student_id += 1;
					ConnectToDatabase.username = rs.getString("username");
					ConnectToDatabase.user_password = rs.getString("user_password");	
				}
				user.add(username);
				user.add(user_password);
				ConnectToDatabase.student_id = student_id;
			
			}catch(Exception e){
				System.out.println("Her skjedde det noe feil: " + e);
				
			}
		} else{
			System.out.println("Ingen ny bruker er lagd");
		}
	
	return (user);
	}
	
	//method that returns the new user's username.
	public String getUserName(){
		ArrayList<String> user = newUser();
		username = user.get(0);
		return username;
	}
	
	//method that returns the new user's password.
	public String getUserPassword(){
		ArrayList<String> user = newUser();
		user_password = user.get(1);
		return user_password;
				
	}
	
	//method that returns the new user's student_id. The student_id is set by the database.
	public int getStudentId(){
		return student_id;
	}
	

}