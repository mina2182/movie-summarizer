<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="./css/style.css" type="text/css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie - Summarizer</title>
</head>
<body>

<div id="line1">
<div id="summarizer">
<%@ include file="SearchEngines.jsp" %>
</div>
<div id="google">
<%@ include file="Google.jsp" %>
</div>
</div>


<table>
<tbody>
<tr>
<td width="40%">
<%
if (request.getAttribute("summary")!=null){
	out.print("summary"+"<br/><br/>");
	out.print(request.getAttribute("summary").toString());
}
%>
</td>
<td width="40%">
<%
if (request.getAttribute("sinopsis")!=null){
	out.print("sinopsis");
	out.print(request.getAttribute("sinopsis").toString());
}
%>
</td>
</tr>
</tbody>
</table>



</body>
</html>