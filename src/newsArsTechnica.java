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
import java.util.StringTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.mysql.jdbc.ResultSet;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
	public class newsArsTechnica 
	{
			  
	  static final String driver = "com.mysql.jdbc.Driver";
	 static final  String url = "jdbc:mysql://localhost:3306/STUDENTS?useUnicode=yes&characterEncoding=UTF-8";
	   
	static final   String username = "om";
	static final   String password = "omtakalkar";
	  
	  public static void main(String [] args) throws ClassNotFoundException , IOException
	  {
	  {
		  
	/*fetching news ******************************************************** from news site*****************	*/  
		  Document doc = Jsoup.connect("http://arstechnica.com/").get();			
			Element content = doc.getElementById("content");
			Elements links = doc.select("a[href]");
			Elements elements = null;
		//	Element document = null;
			 elements=doc.select("p");
		      String text=elements.text();
		        System.out.println(text);

			for (Element link : links) {
			  String linkHref = link.attr("href");
			  String linkText = link.text();
			 // System.out.println("Text::"+linkText+", URL::"+linkHref);
			  //System.out.println(elements.html());
			}
			
			
		  // counting words ***********************************************************
		  String a1 = text;
			String[] splitted = a1.split(" ");
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
	         
	         
	         
	   // Printing noun verbs ***************************************************************************
	         MaxentTagger tagger = new  MaxentTagger ("taggers/english-left3words-distsim.tagger");
	         String a = text;
	         String tagged = tagger.tagString(a1);
	         String taggedString = tagger.tagTokenizedString(tagged);
	         StringTokenizer st = new StringTokenizer(taggedString ," _ ");
	         while (st.hasMoreTokens()) 
	         {  
	             System.out.println(st.nextToken());  
	         }  
	       
	      //   System.out.println(taggedString+"\n");
			       
				//in.close();} 
	         
	// database insertion *****************************************************************************************  
	         
	  Connection conn = null;
		Statement stmt = null ;
		 try {
			 Class.forName(driver);
		      conn = DriverManager.getConnection(url, username, password);
		     
		      stmt = conn.createStatement();
		 //     String tableName = "create table newsFeed(id int primary key,news varchar(255));";
		     
		   
		 //     stmt.executeUpdate(tableName);
		     String query = text;
		  //  String insertintotable = "INSERT INTO newsFeed VALUES (" + 11 + "','" + query + ");";
		    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO newsFeed VALUES (?,?,?)");
		     pstmt.setInt(1, 14);
		        pstmt.setString(2, query);
		        pstmt.setString(3, "abcd");

		        pstmt.executeUpdate();
		    
		    
		//    System.out.println(insertintotable);
		  //    stmt.executeUpdate(insertintotable);
		      
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
	   

	}}