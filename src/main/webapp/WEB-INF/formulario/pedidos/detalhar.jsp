<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<div class="col-lg-12">

    <div class="panel panel-default">

        <div class="panel-heading">
            <fmt:message key="pedido.heading" bundle="${labels}"/>
        </div>

        <div class="panel-body">

            <div class="row">

                <div class="col-sm-1">
                    <div class="form-group" style="margin-top: 25px;">
                        <input type="text" disabled="disabled" id="object.id" value="${object.id}"
                               class="form-control"/>
                    </div>
                </div>

                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label"><fmt:message key='pedido.transacao' bundle='${labels}'/></label>
                        <input type="text" disabled="disabled" id="object.codigoTransacao"
                               value="${object.codigoTransacao}" class="form-control"/>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label"><fmt:message key='pedido.dataCompra' bundle='${labels}'/></label>
                        <input type="text" disabled="disabled" id="object.dataCompra"
                               value="<fmt:formatDate value="${object.dataCompra}" pattern="dd/MM/yyyy" />"
                               class="form-control"/>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label"><fmt:message key='pedido.status' bundle='${labels}'/></label>
                        <input type="text" disabled="disabled" id="object.status" value="${object.status}"
                               class="form-control"/>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="form-group">
                        <label class="control-label"><fmt:message key='pedido.valor' bundle='${labels}'/></label>
                        <fmt:formatNumber value="${object.total}" pattern="0.00" var="preco"/>
                        <input type="text" disabled="disabled" id="object.total" value="${preco}" class="form-control"/>
                    </div>
                </div>

            </div>

            <hr/>
            <jsp:include page="include/cliente.jsp"/>
            <hr/>
            <jsp:include page="include/endereco.jsp"/>

            <hr/>
            <jsp:include page="include/enderecoEntrega.jsp"/>

            <hr/>
            <h4><fmt:message key="itens.heading" bundle="${labels}"/></h4>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th><fmt:message key="produto.nome" bundle="${labels}"/></th>
                    <th><fmt:message key="produto.preco" bundle="${labels}"/></th>
                    <th><fmt:message key="item.quantidade" bundle="${labels}"/></th>
                    <th><fmt:message key="item.tamanho" bundle="${labels}"/></th>
                    <th><fmt:message key="item.total" bundle="${labels}"/></th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${object.itens}" var="item">
                    <tr>
                        <td>${item.produto.nome}</td>
                        <td><fmt:formatNumber value="${item.preco}" pattern="0.00" var="preco"/>${preco}</td>
                        <td>${item.quantidade}</td>
                        <td>
                            <c:choose>
                                <c:when test="${item.atributo != null}">${item.atributo.descricao}</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatNumber value="${item.valor}" pattern="0.00" var="preco"/>${preco}</td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>


        </div>
    </div>
</div>