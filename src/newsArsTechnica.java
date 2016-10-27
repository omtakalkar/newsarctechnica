import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.ResultSet;
	public class newsArsTechnica 
	{
		
	
 
	  
	  static final String driver = "com.mysql.jdbc.Driver";
	 static final  String url = "jdbc:mysql://localhost:3306/STUDENTS";
	   
	static final   String username = "om";
	static final   String password = "omtakalkar";
	  
	  public static void main(String [] args) throws ClassNotFoundException
	  {
	  {
	    System.out.println(readRSS("https://newsapi.org/v1/articles?source=ars-technica&sortBy=top&apiKey=a14d406312954941ad8812396933b9ef"));
	  }
	  
	  Connection conn = null;
		Statement stmt = null;
		 try {
			 Class.forName(driver);
		      conn = DriverManager.getConnection(url, username, password);
		     
		      stmt = conn.createStatement();
		      String tableName = "create table newsFeed " + " (id INT PRIMARY KEY," +" news VARCHAR(255)";
		     
		    String insertintotable = "INSERT INTO newsFeed " +
	                   "VALUES (1, 'newss')";
		     
		      stmt.executeUpdate(insertintotable);
		      stmt.executeUpdate(tableName);
		   //  conn.commit();
		     ResultSet rs;
		     rs = (ResultSet) stmt.executeQuery("SELECT * from newsFeed");
           while ( rs.next() )
           {
               String arstechnicanews = rs.getString("news");
               System.out.println(arstechnicanews);
           }
		      
		  
		    } 
		 catch (ClassNotFoundException e) 
		 {
		      System.out.println(" failed to load MySQL driver.");
		      e.printStackTrace();
		 } 
		 catch (SQLException e) 
		 {
		      System.out.println("error: failed to create a connection");
		      e.printStackTrace();
	     } 
		 catch (Exception e)
		 {
		      System.out.println("other error:");
		      e.printStackTrace();
		 } 
		finally
		{
		      try
		      {
		        stmt.close();
		        conn.close();        
		      }
		     catch (SQLException e)
		      {
		        e.printStackTrace();
		      }
		}
		       
	  }
	  
	  
	//  public static Connection getConnection() throws Exception 
	//  {
		//  System.out.println("1st");
		  
		 
		    
		   
	//	  Connection conn = DriverManager.getConnection(url,username,password);
		 
	//	  System.out.println("2nd");
		 //   return conn;
		   
	//  }
	  
	  public static String readRSS( String urlAddress) 
	  {
	  try
	 {
	    URL rssURL = new URL(urlAddress);
	       
	    BufferedReader in = new BufferedReader (new InputStreamReader(rssURL.openStream()));
	    
	    
	    String line;
		String  sourceCode = "";
		
		while ((line = in.readLine()) != null) 
		{
		    int titleEndIndex = 0;
		    int titleStartIndex = 0;
		   
		    while (titleStartIndex >= 0)
		    {
		        titleStartIndex = line.indexOf("title", titleEndIndex);
		        if (titleStartIndex >= 0)
		        {
		            titleEndIndex = line.indexOf("," ,titleStartIndex);
		            sourceCode += line.substring(titleStartIndex + "title".length(), titleEndIndex) + "\n";
		            
		        }
		        
		    }
		   
		    
		}
		String[] splitted = sourceCode.split(" ");
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (int i=0; i<splitted.length ; i++)
        {
            if (hm.containsKey(splitted[i])) 
            {
               int cont = hm.get(splitted[i]);
               hm.put(splitted[i], cont + 1);
            } else 
            {
               hm.put(splitted[i], 1);
            }
         }
        
         System.out.println(hm+"\n");
         
      //   String tagged = sourceCode;	
  		
         
         
  	//	System.out.println(String.format("verbs %d", verbs));
         
         
         
        
		
 		
         
		in.close(); 
	return sourceCode;
	
	 }
	catch(IOException ioe)
	{
	  System.out.println("error");
	}
	return urlAddress;
	}}
	

