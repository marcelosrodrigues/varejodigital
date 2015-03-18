<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>

<div class="row">

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='endereco.estado' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.cliente.endereco.estado.nome"
                   value="${object.cliente.endereco.estado.nome}" class="form-control"/>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='endereco.cidade' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.cliente.endereco.cidade"
                   value="${object.cliente.endereco.cidade}" class="form-control"/>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='endereco.bairro' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.cliente.endereco.bairro"
                   value="${object.cliente.endereco.bairro}" class="form-control"/>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='endereco.cep' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.cliente.endereco.cep"
                   value="${object.cliente.endereco.cep}" class="form-control"/>
        </div>
    </div>
</div>

<div class="form-group">
    <label class="control-label"><fmt:message key='endereco.logradouro' bundle='${labels}'/></label>
    <input type="text" disabled="disabled" id="object.cliente.endereco.logradouro"
           value="${object.cliente.endereco.logradouro}" class="form-control"/>
</div>

<div class="row">

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='usuario.residencial' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.cliente.endereco.telefone"
                   value="${object.cliente.endereco.telefone}" class="form-control"/>
        </div>
    </div>

    <div class="col-sm-3">
        <div class="form-group">
            <label class="control-label"><fmt:message key='usuario.celular' bundle='${labels}'/></label>
            <input type="text" disabled="disabled" id="object.cliente.endereco.celular"
                   value="${object.cliente.endereco.celular}" class="form-control"/>
        </div>
    </div>
</div>