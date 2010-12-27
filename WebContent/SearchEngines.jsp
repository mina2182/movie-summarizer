<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!--  http://www.apl.jhu.edu/~hall/java/Servlet-Tutorial/Servlet-Tutorial-Response-Status-Line.html -->
<HTML>
<HEAD>
  <TITLE>Searching the Web</TITLE>
</HEAD>

<BODY>
<H1>Searching the Web</H1>

<FORM ACTION="SearchEngines" METHOD="POST">
  <table>
  <tbody>
  	<tr>
    	<td>Search Movies :</td> 
    	<td><INPUT TYPE="TEXT" NAME="searchString"></td> 
    </tr>
    <tr>
    	<td colspan = "3"><input type="radio" name ="menu" value="my"/>relevance
    	<input type="radio" name ="menu" value="bobot"/>bobot
    	<input type="radio" name ="menu" value="lucene"/>searcher</td>
    </tr>
    <tr>
    	<td><INPUT TYPE="SUBMIT" VALUE="Summary"></td>
    </tr>
  </tbody>
  </table>
</FORM>

</BODY>
</HTML>

