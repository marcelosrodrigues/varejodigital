<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<h4><fmt:message key="pedido.endereco.heading" bundle="${labels}"/></h4>

<div class="row">

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='endereco.estado' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.enderecoEntrega.estado.nome"
                   value="${object.enderecoEntrega.estado.nome}" class="form-control"/>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='endereco.cidade' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.enderecoEntrega.cidade"
                   value="${object.enderecoEntrega.cidade}" class="form-control"/>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='endereco.bairro' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.enderecoEntrega.bairro"
                   value="${object.enderecoEntrega.bairro}" class="form-control"/>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='endereco.cep' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.enderecoEntrega.cep" value="${object.enderecoEntrega.cep}"
                   class="form-control"/>
        </div>
    </div>
</div>

<div class="form-group">
    <label class="control-label"><fmt:message key='endereco.logradouro' bundle='${labels}'/></label>
    <input type="text" disabled="disabled" id="object.enderecoEntrega.logradouro"
           value="${object.enderecoEntrega.logradouro}" class="form-control"/>
</div>

