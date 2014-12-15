<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="labels"  scope="application" var="labels" />
<div class="col-lg-12">

    <div class="panel panel-default">

        <div class="panel-heading">
            <fmt:message key="loja.heading" bundle="${labels}" />
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
                    <form action="<c:url value='/loja/salvar.do' />" method="post" role="form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="object.id" value="${object.id}" />

                        <div class="form-group">
                            <label for="object.nome"><fmt:message key="loja.nome" bundle="${labels}" /></label>
                            <input type="text" value="${object.nome}" id="object.nome" name="object.nome" class="form-control" placeholder="<fmt:message key='loja.nome' bundle='${labels}'/>" />
                        </div>

                        <button type="submit" value="salvar" class="btn btn-default btn-info"><fmt:message key="button.salvar" bundle="${labels}" /></button>

                    </form>
                </div>
            </div>

        </div>

    </div>

</div>
