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

<div id="line2">
<div id="result">
result
<%
if (request.getAttribute("summary")!=null)
	out.print(request.getAttribute("summary").toString());
%>
</div>
</div>

</body>
</html>