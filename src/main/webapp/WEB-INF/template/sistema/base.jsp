<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Portal Ella S/A</title>


    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/metisMenu/metisMenu.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/timeline.css' />" rel="stylesheet">
    <link href="<c:url value='/css/sb-admin-2.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/morris.css' />" rel="stylesheet">
    <link href="<c:url value='/font-awesome-4.1.0/css/font-awesome.min.css' />" rel="stylesheet" type="text/css">
    <link href='<c:url value="/css/jquery-ui-1.10.4.custom.min.css" />' rel="stylesheet" >

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div id="wrapper">

<!-- Navigation -->
    <jsp:include page="includes/menu.jsp" />

    <div id="page-wrapper" style="min-height: 593px;margin-top: 50px;">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><tiles:getAsString name="header" /></h1>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <tiles:insertAttribute name="conteudo" />
                </div>
            </div>
        </div>
    </div>

<!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="<c:url value='/js/jquery.js' />"></script>
<script src="<c:url value='/js/bootstrap.min.js' />"></script>
<script src="<c:url value='/js/plugins/metisMenu/metisMenu.min.js' />"></script>
<script src="<c:url value='/js/sb-admin-2.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.color.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.maskMoney.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-maskinput.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-datepicker-pt.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/ui.js' />" ></script>

</body>

</html>
