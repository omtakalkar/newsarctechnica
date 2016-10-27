import java.sql.*;

public class creatingTable 
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost:3306/STUDENTS";;

	static final String USER = "om";
	static final String PASS = "omtakalkar";

	public static void main (String[] args)
	{
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("connecting to STUDENTS");
			conn = DriverManager.getConnection(URL,USER,PASS);
			System.out.println("connected");
			System.out.println("creating a table in STUDENTS");
			
			stmt = conn.createStatement();
			
			String sql = "CREATE TABLE REGISTRATION" +
						 "(id INTEGER not NULL," +
						 "news VARCHAR(255)," +
						 "PRIMARY KEY(id))";
			
			stmt.executeUpdate(sql);
			System.out.println("table created");
				 
			String sqll = "INSERT INTO Registration " +
	                   "VALUES (100, 'Zara', 'Ali', 18)";
	      stmt.executeUpdate(sqll);
	      sql = "INSERT INTO Registration " +
	                   "VALUES (101, 'Mahnaz', 'Fatma', 25)";
	      stmt.executeUpdate(sqll);
	      sql = "INSERT INTO Registration " +
	                   "VALUES (102, 'Zaid', 'Khan', 30)";
	      stmt.executeUpdate(sqll);
	      sql = "INSERT INTO Registration " +
	                   "VALUES(103, 'Sumit', 'Mittal', 28)";
	      stmt.executeUpdate(sqll);
	      System.out.println("Inserted records into the table...");
			
			
		}
		catch(SQLException se)
		{
		      se.printStackTrace();
		 }
		catch(Exception e)
		{
		      e.printStackTrace();
	    }
		finally
		{
		      try
		      {
		         if(stmt!=null)
		            conn.close();
		      }
		      catch(SQLException se)
		      {
		      }
		      try
		      {
		         if(conn!=null)
		            conn.close();
		      }
		      catch(SQLException se)
		      {
		         se.printStackTrace();
		      }
		 	}
		   System.out.println("Goodbye!");
	}
}
