<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<div class="col-lg-12">

    <div class="panel panel-default">

        <div class="panel-heading">
            <fmt:message key="perfil.heading" bundle="${labels}"/>
        </div>

        <div class="panel-body">

            <div class="row">
                <div class="col-lg-11">
                    <form action="<c:url value='/perfil/salvar.do' />" method="post" role="form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="object.id" value="${object.id}"/>

                        <div class="form-group">
                            <label class="control-label"><fmt:message key='perfil.nome' bundle='${labels}'/></label>
                            <input type="text" disabled="disabled" id="object.nome" value="${object.nome}"
                                   class="form-control"/>
                        </div>

                        <label for="usuario" class="control-label">Usuario</label>

                        <div class="form-group input-group">
                            <input id="usuario" type="text" class="form-control" placeholder="Usuario">
                            <span class="input-group-btn">
                                <button id="pesquisar-usuario" class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>

                        <div id="usuarios" style="display:none">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th><fmt:message key="usuario.nome" bundle="${labels}"/></th>
                                    <th><fmt:message key="usuario.email" bundle="${labels}"/></th>
                                    <th style="width:5%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                        <h4>Membros</h4>

                        <div id="membros">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th><fmt:message key="usuario.nome" bundle="${labels}"/></th>
                                    <th><fmt:message key="usuario.email" bundle="${labels}"/></th>
                                    <th style="width:5%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${object.membros}" var="usuario">
                                    <tr>
                                        <td>${usuario.nomeCompleto}</td>
                                        <td>${usuario.email}</td>
                                        <td align="center">
                                            <button type="button" class="btn btn-danger btn-circle"
                                                    usuario="${usuario.id}" nome="${usuario.nomeCompleto}"
                                                    onclick="javascript:removerUsuario(this);">
                                                <i class="fa fa-times"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <button type="submit" value="salvar" class="btn btn-default btn-info"><fmt:message
                                key="button.salvar" bundle="${labels}"/></button>

                    </form>
                </div>
            </div>


        </div>

    </div>

</div>
