<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<div class="col-lg-12">
    <c:if test="${not empty sucesso}">
        <div class="alert alert-dismissable alert-success">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong>${sucesso}</strong>
        </div>
    </c:if>

    <form action="<c:url value='/perfil/pesquisar.do'/>" role="form">
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <input type="text" name="object.nome" id="object.nome"
                           placeholder="<fmt:message key='loja.nome' bundle='${labels}'  />" class="form-control"/>
                </div>
            </div>
            <div class="col-sm-6">
                <button type="submit" value="pesquisar" class="btn btn-info"><fmt:message key="button.pesquisar"
                                                                                          bundle="${labels}"/></button>
            </div>
        </div>
    </form>


    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th style="width:10%">#</th>
            <th><fmt:message key="perfil.nome" bundle="${labels}"/></th>
            <th style="width:15%">N° de Membros</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultlist.list}" var="object">
            <tr>
                <td><a href='<c:url value="/perfil/abrir.do?id=${object.id}" />'>${object.id}</a></td>
                <td>${object.nome}</td>
                <td align="right">${fn:length(object.membros)}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <pmrodrigues:paginate url="/perfil/pesquisar.do" resultList="${resultlist}"/>

</div>
