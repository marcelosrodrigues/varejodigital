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

    <form action="<c:url value='/pedido/pesquisar.do'/>" role="form">
        <div class="row">
            <div class="col-sm-2">
                <div class="form-group ">
                    <label class="control-label" for="object.status"><fmt:message key="pedido.status"
                                                                                  bundle="${labels}"/></label>
                    <select name="object.status" id="object.status" class="form-control">
                        <option></option>
                        <option value="0">Completo</option>
                        <option value="1">Aguardando Pagamento</option>
                        <option value="2">Aprovado</option>
                        <option value="3">Pagamento em análise</option>
                        <option value="4">Cancelado</option>
                        <option value="6">Devolvida</option>
                        <option value="7">Estornado</option>
                        <option value="8">Erro</option>
                    </select>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group">
                    <label class="control-label" for="object.cliente.primeiroNome"><fmt:message
                            key='cliente.primeiroNome' bundle='${labels}'/></label>
                    <input type="text" name="object.cliente.primeiroNome" id="object.cliente.primeiroNome"
                           placeholder="<fmt:message key='cliente.primeiroNome' bundle='${labels}'  />"
                           class="form-control"/>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group">
                    <label class="control-label" for="object.cliente.ultimoNome"><fmt:message key='cliente.ultimoNome'
                                                                                              bundle='${labels}'/></label>
                    <input type="text" name="object.cliente.ultimoNome" id="object.cliente.ultimoNome"
                           placeholder="<fmt:message key='cliente.ultimoNome' bundle='${labels}'  />"
                           class="form-control"/>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label class="control-label" for="object.status">Data da Compra</label>
                    <input type="date" name="object.dataCompra" id="object.dataCompra"
                           placeholder="<fmt:message key='pedido.dataCompra' bundle='${labels}'  />"
                           class="form-control"/>
                </div>
            </div>
            <div class="col-sm-2" style="margin-top: 25px;">
                <button type="submit" value="pesquisar" class="btn btn-info">
                    <fmt:message key="button.pesquisar" bundle="${labels}"/>
                </button>
            </div>
        </div>

    </form>

    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th style="width:10%">#</th>
            <th><fmt:message key="pedido.transacao" bundle="${labels}"/></th>
            <th><fmt:message key="cliente.nome" bundle="${labels}"/></th>
            <th><fmt:message key="pedido.status" bundle="${labels}"/></th>
            <th><fmt:message key="pedido.dataCompra" bundle="${labels}"/></th>
            <th><fmt:message key="pedido.valor" bundle="${labels}"/></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultlist.list}" var="object">
            <tr>
                <td><a href='<c:url value="/pedido/abrir.do?id=${object.id}" />'>${object.id}</a></td>
                <td>${object.codigoTransacao}</td>
                <td>${object.cliente.nome}</td>
                <td>${object.status}</td>
                <td><fmt:formatDate value="${object.dataCompra}" pattern="dd/MM/yyyy"/></td>
                <td><fmt:formatNumber value="${object.total}" pattern="0.00" var="preco"/>${preco}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <pmrodrigues:paginate url="/pedido/pesquisar.do" resultList="${resultlist}"/>

</div>
