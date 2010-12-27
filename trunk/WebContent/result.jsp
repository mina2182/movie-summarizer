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


<table>
<tbody>
<tr>
<td>
<div id ="logoresult">
<img src="./img/logo.jpg" width="60%" height="150px"/>
</div>
<div id="line1">
<div id="summary">
<%
if (request.getAttribute("summary")!=null){
	out.print("summary"+"<br/><br/>");
	out.print(request.getAttribute("summary").toString());
}
%>
</div>
<div id="sinopsis">
<%
if (request.getAttribute("sinopsis")!=null){
	out.print("sinopsis");
	out.print(request.getAttribute("sinopsis").toString());
}
%>
</div>
</div>

</td>
</tr>
</tbody>
</table>



</body>
</html>