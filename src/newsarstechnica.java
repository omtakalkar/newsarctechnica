	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.URL;
	public class newsarstechnica 
	{
	  public static void main(String [] args)
	  {
	    System.out.println(readRSS("https://newsapi.org/v1/articles?source=ars-technica&sortBy=top&apiKey=a14d406312954941ad8812396933b9ef"));
	  }

	  public static String readRSS( String urlAddress)
	  {
	  try
	 {
	    URL rssURL = new URL(urlAddress);
	       
	    BufferedReader in = new BufferedReader (new InputStreamReader(rssURL.openStream()));
	    
	    String line;
		String  sourceCode = "";
		while ((line = in.readLine()) != null) {
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

