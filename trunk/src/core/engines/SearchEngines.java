package core.engines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	    // The URLEncoder changes spaces to "+" signs and other
	    // non-alphanumeric characters to "%XY", where XY is the
	    // hex value of the ASCII (or ISO Latin-1) character.
	    // The getParameter method decodes automatically, but since
	    // we're just passing this on to another server, we need to
	    // re-encode it.
	    String searchString = request.getParameter("searchString");
	    String menu = request.getParameter("menu");
	    URL theurl = new URL(searchString);
	    System.setProperty("http.proxyHost", "152.118.24.10");
	    System.setProperty("http.proxyPort", "8080");

	    BufferedReader in = new BufferedReader(new InputStreamReader(theurl.openStream()));
	    String line;
	    int id = 0;
	    String content[] = new String [1000];
	    while ((line = in.readLine())!=null){
	    	line = line.trim();
	    	//System.out.println(line);
	    	if (line.matches("<p>[A-Z](.*)")){
	    		content[id] = line;
	    		id++;
	    		//System.out.println(line);
	    	}
	    }
	    in.close();
	    Reader reader = new Reader(content);
	    String summary = "";
	    if (menu.equals("lucene")){
	    	summary = reader.luceneSummarizer();
		}else{
			summary = reader.mySummarizer();
		}
	    String sinopsis = reader.getSinopsis();
	    request.setAttribute("sinopsis", sinopsis);
	    request.setAttribute("summary", summary);
	    getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);

	}

}
