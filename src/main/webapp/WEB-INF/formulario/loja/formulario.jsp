<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<div class="col-lg-12">

    <div class="panel panel-default">

        <div class="panel-heading">
            <fmt:message key="loja.heading" bundle="${labels}"/>
        </div>

        <div class="panel-body">

            <pmrodrigues:summary text="Ocorreu um erro na validação dos seus dados"/>

            <div class="row">
                <div class="col-lg-11">
                    <form action="<c:url value='/loja/salvar.do' />" method="post" role="form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="object.id" value="${object.id}"/>

                        <pmrodrigues:textfield label="loja.nome" value="${object.nome}" id="object.nome"
                                               errorField="nome"/>

                        <pmrodrigues:checkbox id="object.secoes"
                                              label="Departamentos"
                                              value="${departamentos}"
                                              labelField="nome"
                                              valueField="id"
                                              checked="${object.secoes}"/>

                        <button type="submit" value="salvar" class="btn btn-default btn-info"><fmt:message
                                key="button.salvar" bundle="${labels}"/></button>

                    </form>
                </div>
            </div>


        </div>

    </div>

</div>
