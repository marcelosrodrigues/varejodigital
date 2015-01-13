<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>

<pmrodrigues:treeview
        id="departamentos"
        list="${resultlist.list}"
        label="nome"
        value="id"
        subList="subsecoes"
        url="/secao/abrir.do"/>
<br/>
<a href="<c:url value='/secao/novo.do' />" class="btn btn-info">Adicionar</a>