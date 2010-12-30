<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href='http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="./css/style.css" type="text/css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
body { font-family: Verdana; font-size: 12px; margin: 0 10px; background-color:#f1f1f1;}
h1 {font-family: 'Yanone Kaffeesatz', arial, serif; text-align : center; font-size: 38px;margin-top: 45px;}
h2,h3 {padding: 5px 0px 5px 20px; margin: 0; font-family: Cambria, Georgia, Helvetica; font-size: 17px; }
form { padding: 2px; border-bottom: solid 2px #ccc; }
p { margin: 0 2px; padding: 5px; }
input { padding: 4px; border-radius: 5px; -moz-border-radius: 5px; -webkit-border-radius: 5px;}
.search-box {width: 400px;}
.wrap { width: 980px; margin: 0 auto; background-color:#fff; padding: 10px; }
.movieInfo { width: 600px; line-height: 25px; padding-left: 5px; }
.shadow { 
	-moz-box-shadow: 5px 5px 5px #ccc; 
	-webkit-box-shadow: 5px 5px 5px #ccc; 
	box-shadow: 5px 5px 5px #ccc;
}	

</style>
<title>Movie - Summarizer</title>
</head>
<body>
<h1 style="color: #069;">Movie Summarizer</h1>
<div id="block">
<table>
<tbody>
<tr>
<td id="grid">
<div id="line1">
<div id="summarizer">
<%@ include file="SearchEngines.jsp" %>
</div>
<div id="google">
<%@ include file="Google.jsp" %>
</div>
</div>

</td>
</tr>
</tbody>
</table>

</div>
</body>
</html>