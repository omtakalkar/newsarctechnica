import java.io.BufferedReader;
import java.io.FileReader;
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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.mysql.jdbc.ResultSet;

import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreNLPProtos.CorefChain;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
	public class newsArsTechnica 
	{
		
	
 
	  
	  static final String driver = "com.mysql.jdbc.Driver";
	 static final  String url = "jdbc:mysql://localhost:3306/STUDENTS";
	   
	static final   String username = "om";
	static final   String password = "omtakalkar";
	static	Document doc = null;
	static String text=null;
	static  StanfordCoreNLP pipeline;	  

	
	
	
	  public static void main(String [] args) throws ClassNotFoundException , IOException
	  {
	  {
		  
	  
	 
		  String[] siteURL = {"http://arstechnica.com/", "https://www.cnet.com/news/","http://www.digitaltrends.com/computing/"} ;
		  int size = siteURL.length;
		  for (int i=0; i<=size  ; i++)
			  
		  {
			//  System.out.println(siteURL[i]); 
			  
		   doc = Jsoup.connect(siteURL[i]).get();
	// i++;
		  

			//Element content = doc.getElementById("content");
			//Elements links = doc.select("a[href]");
			Elements elements = null;
		//	Element document = null;
			 elements=doc.select("p");
		      String text=elements.text();
		        System.out.println("\t" +text +"\n");
		        
		    
		        
		        

		        
		        
		        
		        Properties props = new Properties();
		        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
		        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		       
		        String news = text;
		        Annotation document = new Annotation(news);
		        pipeline.annotate(document);
		        
		        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		        for (CoreMap sentence : sentences) 
		        {
		            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
		            System.out.println(sentiment + "\t" + sentence);
		        }
		        
		        
		        
		  
		        
		
	  
	  String a1 = text;
		String[] splitted = a1.split(" ");
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (int i1=0; i1<splitted.length ; i1++)
        {
            if (hm.containsKey(splitted[i1])) 
            {
               int cont = hm.get(splitted[i1]);
               hm.put(splitted[i1], cont + 1);
            } else 
            {
               hm.put(splitted[i1], 1);
            }
         }
        
         System.out.println(hm+"\n");
		  

         
             
         MaxentTagger tagger = new  MaxentTagger ("taggers/english-left3words-distsim.tagger");
     //    String a = text;
         String tagged = tagger.tagString(a1);
         String taggedString = tagger.tagTokenizedString(tagged);
   /*      StringTokenizer st = new StringTokenizer(taggedString ," _ ");
         while (st.hasMoreTokens()) 
         {  
             System.out.println(st.nextToken());  
         }  
      */ 
         System.out.println(taggedString+"\n");
         
         
         Connection conn = null;
 		Statement stmt = null ;
 		 try {
 			 Class.forName(driver);
 		      conn = DriverManager.getConnection(url, username, password);
 		     
 		      stmt = conn.createStatement();
 		 //     String tableName = "create table newsFeed(id int primary key,news varchar(255));";
 		     
 		   
 		 //     stmt.executeUpdate(tableName);
 		     
 		      String query =text;
 		   PreparedStatement pstmt = conn.prepareStatement("INSERT IGNORE newsFeed(id,news) VALUES (?,?)");
		     pstmt.setInt(1, 3);
		        pstmt.setString(2, query);
		       pstmt.executeUpdate();
		      
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
 	  
 	  

	  }

	}}