<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<div class="col-lg-12">


    <c:if test="${not empty sucesso}">
        <div class="alert alert-dismissable alert-success">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong>${sucesso}</strong>
        </div>
    </c:if>

    <form action="<c:url value='/produto/pesquisar.do'/>" role="form">
        <div class="row">
            <div class="col-sm-3">
                <div class="form-group" style="margin-top: 25px;">
                    <input type="text" name="object.nome" id="object.nome"
                           placeholder="<fmt:message key='produto.nome' bundle='${labels}'  />" class="form-control"/>
                </div>
            </div>
            <div class="col-sm-5">
                <pmrodrigues:combobox label="loja.heading" errorField="loja" list="${lojas}" id="object.loja"
                                      valueField="id" labelField="nome" value="${object.loja}"/>
            </div>
            <div class="col-sm-4" style="margin-top: 25px;">
                <button type="submit" value="pesquisar" class="btn btn-info"><fmt:message key="button.pesquisar"
                                                                                          bundle="${labels}"/></button>
            </div>
        </div>
    </form>

    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th style="width:10%">#</th>
            <th><fmt:message key="produto.nome" bundle="${labels}"/></th>
            <th><fmt:message key="departamento.heading" bundle="${labels}"/></th>
            <th><fmt:message key="produto.preco" bundle="${labels}"/></th>
            <th><fmt:message key="produto.peso" bundle="${labels}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultlist.list}" var="object">
            <tr>
                <td><a href='<c:url value="/produto/abrir.do?id=${object.id}" />'>${object.id}</a></td>
                <td>${object.nome}</td>
                <td>${object.secao.nome}</td>
                <td><fmt:formatNumber value="${object.preco}" pattern="0.00" var="preco"/>${preco}</td>
                <td><fmt:formatNumber value="${object.peso}" pattern="0.00" var="peso"/>${peso}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <pmrodrigues:paginate url="/produto/pesquisar.do" resultList="${resultlist}"/>
    <div class="row">
        <div class="col-sm-6" style="margin-top: -40px;">
            <a href="<c:url value='/produto/novo.do' />" class="btn btn-info">Adicionar</a>
        </div>
    </div>


</div>