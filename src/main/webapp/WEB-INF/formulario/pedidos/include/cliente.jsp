<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<h4><fmt:message key="cliente.heading" bundle="${labels}"/></h4>

<div class="row">

    <div class="col-sm-6">
        <div class="form-group">
            <label class="control-label"><fmt:message key='cliente.nomeCompleto' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.cliente.nome" value="${object.cliente.nome}"
                   class="form-control"/>
        </div>
    </div>

    <div class="col-sm-4">
        <div class="form-group">
            <label class="control-label"><fmt:message key='cliente.dataNascimento' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.cliente.dataNascimento"
                   value="<fmt:formatDate value="${object.cliente.dataNascimento}" pattern="dd/MM/yyyy" />"
                   class="form-control"/>
        </div>
    </div>

</div>

<div class="form-group">
    <label class="control-label"><fmt:message key='usuario.email' bundle='${labels}'/></label>
    <input type="text" disabled="disabled" id="object.cliente.email" value="${object.cliente.email}"
           class="form-control"/>
</div>