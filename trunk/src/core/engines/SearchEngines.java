package core.engines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchEngines
 */
public class SearchEngines extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchEngines() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	    // The URLEncoder changes spaces to "+" signs and other
	    // non-alphanumeric characters to "%XY", where XY is the
	    // hex value of the ASCII (or ISO Latin-1) character.
	    // The getParameter method decodes automatically, but since
	    // we're just passing this on to another server, we need to
	    // re-encode it.
	    String searchString = URLEncoder.encode(request.getParameter("searchString"));
	    
	    URL theurl = new URL("http://themoviespoiler.com/Spoilers/"+ searchString +".html");
	    System.setProperty("http.proxyHost", "152.118.24.10");
	    System.setProperty("http.proxyPort", "8080");

	    BufferedReader in = new BufferedReader(new InputStreamReader(theurl.openStream()));
	    String line;
	    while ((line = in.readLine())!=null){
	    	line = line.trim();
	    	if (Pattern.matches("<p>", line)){
	    		System.out.println(line);
	    	}
	    }
	    in.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);

	}

}
