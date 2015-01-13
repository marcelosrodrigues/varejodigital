<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="labels" scope="application" var="labels"/>


<div class="col-lg-12">

    <div class="panel panel-default">

        <div class="panel-heading">
            <fmt:message key="departamento.heading" bundle="${labels}" />
        </div>

        <div class="panel-body">

            <c:if test="${not empty errors}">
                <div class="alert alert-dismissable alert-danger">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <strong>Ocorreu um erro na validação dos seus dados</strong><br/>
                    <c:forEach items="${errors}" var="error">
                        <p>${error.message}</p>
                    </c:forEach>
                </div>
            </c:if>

            <div class="row">
                <div class="col-lg-10">
                    <form action="<c:url value='/secao/salvar.do' />" method="post" role="form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="object.id" value="${object.id}" />

                        <select name="object.pai.id" id="object.pai.id" class="form-control">
                            <option></option>
                            <c:forEach items="${departamentos}" var="departamento">
                                <option value="${departamento.id}"  <c:if test="${departamento.id == object.pai.id}">selected="selected"</c:if>>${departamento.nome}</option>
                            </c:forEach>

                        </select>

                        <div class="form-group">
                            <label for="object.nome"><fmt:message key="departamento.nome" bundle="${labels}" /></label>
                            <input type="text" value="${object.nome}" id="object.nome" name="object.nome" class="form-control" placeholder="<fmt:message key='departamento.nome' bundle='${labels}'/>" />
                        </div>

                        <button id="salvar" type="submit" value="salvar" class="btn btn-default btn-info"><fmt:message key="button.salvar" bundle="${labels}" /></button>
                        <c:if test="${object.id gt 0}">
                            <a href="<c:url value='/secao/${object.id}/novo.do'/>" class="btn btn-default btn-info">Adicionar novo departamento</a>
                        </c:if>
                    </form>
                </div>
            </div>

        </div>

    </div>

</div>