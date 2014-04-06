<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Portal ELLA S/A</title>
<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css" />'>
<link rel="stylesheet" href='<c:url value="/css/main.css" />'>
<link rel="stylesheet" href='<c:url value="/css/jquery-ui-1.10.4.custom.min.css" />'>
<link rel="stylesheet" href='<c:url value="/css/boleto.css" />'>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/bootstrap.min.js' />" ></script>

</head>
<body>
<div id="header" class="navbar navbar-default navbar-fixed-top"></div>
<div id="container" class="container">
	<tiles:insertAttribute name="conteudo" />
</div>


<script type="text/javascript" src="<c:url value='/js/jquery.color.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.maskMoney.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-maskinput.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-datepicker-pt.js' />" ></script>

<script type="text/javascript" src="<c:url value='/js/ui.js' />" ></script>

</body>

</html>