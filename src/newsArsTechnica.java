
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import edu.stanford.nlp.util.StringUtils;
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
			  // display news ******************************************
			  	doc = Jsoup.connect(siteURL[i]).get();
			  	Elements elements = null;
				elements=doc.select("p");
			    String text=elements.text();
			    System.out.println("\t" +text +"\n");
			        
		    
			        //display sentiment**********************************************
		        Properties props = new Properties();
		        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
		        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		       
		        String news = text;
		        String sentiment = null ;
		        Annotation document = new Annotation(news);
		        pipeline.annotate(document);
		        
		        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		        for (CoreMap sentence : sentences) 
		        {
		           sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
		            System.out.println(sentiment + "\t" + sentence);
		        }
		        
		        
		        
		        //counting words*****************************
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
				  

         
	           //noun and vers **************************
	         MaxentTagger tagger = new  MaxentTagger ("taggers/english-left3words-distsim.tagger");
	         // String abc = text;
	         String tagged = tagger.tagString(text);
	         String taggedString = tagger.tagTokenizedString(tagged);
	         System.out.println(taggedString+"\n");
	         
	         
	         
	         
	       
         
         
		         //insert into database *********************************
		         Connection conn = null;
		 		Statement stmt = null ;
		 		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		 		 try {
					 			 Class.forName(driver);
			 		      conn = DriverManager.getConnection(url, username, password);
			 		     
			 		      
			 		   
			 		      
			 		      stmt = conn.createStatement();
			 		      PreparedStatement pstmt = conn.prepareStatement("INSERT IGNORE newsFeed(id,news) VALUES (?,?)");
			 		      pstmt.setInt(1, 1);
					      pstmt.setString(2, text);
					      pstmt.executeUpdate();
					     
					      
					      pstmt =conn.prepareStatement("INSERT IGNORE sentiment(sentiments,text,register_date) VALUES (?,?,?)");
			 		      pstmt.setLong(1,1);
					      pstmt.setString(2, sentiment);
					      pstmt.setTimestamp(3, date);
					      
					      
				 		  
				 		  pstmt.executeUpdate();
					      ResultSet rs;
					      rs = (ResultSet) stmt.executeQuery("SELECT * from sentiment");
				            while ( rs.next() )
				            {
				                String arstechnicanews = rs.getString("text");
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
 		       
		 		//counting sentiments*********************************
		 		List<String> list = new ArrayList<String>();
		 		list.add(sentiment);
		 		

		 		Set<String> unique = new HashSet<String>(list);
		 		for (String key : unique)
		 		{
		 			 System.out.println("counting positive negative words");
		 		    System.out.println(key + ": " + Collections.frequency(list, key));
		 		    
		 		}
 	  }
 	  
 	  

		  }	  

	}}