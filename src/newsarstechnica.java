import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
	public class newsarstechnica 
	{
	  public static void main(String [] args) throws ClassNotFoundException, SQLException
	  {
	    System.out.println(readRSS("https://newsapi.org/v1/articles?source=ars-technica&sortBy=top&apiKey=a14d406312954941ad8812396933b9ef"));
	  }

	  public static String readRSS( String urlAddress) throws ClassNotFoundException, SQLException
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
         
         Connection conn = null;
 		Statement stmt = null;
 		PreparedStatement preparedStatement = null;
 		
 		
 		
 		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("connecting to STUDENTS");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/STUDENTS","om","omtakalkar");
		System.out.println("connected");
		
		
		
         
         
		in.close(); 
	return sourceCode;
	
	}
	catch(IOException ioe)
	{
	  System.out.println("error");
	}
	return urlAddress;
	}
	}  

