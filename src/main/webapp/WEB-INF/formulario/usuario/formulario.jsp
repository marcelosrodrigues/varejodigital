<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>
<div class="col-lg-12">

    <div class="panel panel-default">

        <div class="panel-heading">
            <fmt:message key="usuario.heading" bundle="${labels}"/>
        </div>

        <div class="panel-body" style="">

            <c:if test="${not empty errors}">
                <div class="alert alert-dismissable alert-danger">
                    <button type="button" class="close" data-dismiss="alert">�</button>
                    <strong>Ocorreu um erro na valida��o dos seus dados</strong><br/>
                    <c:forEach items="${errors}" var="error">
                        <p>${error.message}</p>
                    </c:forEach>
                </div>
            </c:if>

            <div class="row">
                <div class="col-lg-12">
                    <form action="<c:url value='/usuario/salvar.do' />" method="post" role="form">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="object.id" value="${object.id}"/>


                        <div class="row">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="object.nomeCompleto"><fmt:message key="usuario.nome"
                                                                                  bundle="${labels}"/></label>
                                    <input type="text" value="${object.nomeCompleto}" id="object.nomeCompleto"
                                           name="object.nomeCompleto" class="form-control"
                                           placeholder="<fmt:message key='usuario.nome' bundle='${labels}'/>"/>
                                </div>
                            </div>
                            <div class="col-sm-4" style="margin-left: 8px;">
                                <div class="form-group">
                                    <label for="object.dataNascimento"><fmt:message key="usuario.dataNascimento"
                                                                                    bundle="${labels}"/></label>
                                    <input type="date" name="object.dataNascimento" id="object.dataNascimento"
                                           value="${object.dataNascimento}" class="form-control" pattern="dd/MM/yyyy"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">

                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="object.email"><fmt:message key="usuario.email"
                                                                           bundle="${labels}"/></label>
                                    <input type="text" value="${object.email}" id="object.email" name="object.email"
                                           class="form-control"
                                           placeholder="<fmt:message key='usuario.email' bundle='${labels}'/>"/>
                                </div>
                            </div>

                            <div class="col-sm-4" style="margin-left: 8px;">
                                <div class="form-group">
                                    <label for="object.cpf"><fmt:message key="usuario.cpf" bundle="${labels}"/></label>
                                    <input type="text" value="${object.cpf}" id="object.cpf" name="object.cpf"
                                           class="form-control"
                                           placeholder="<fmt:message key='usuario.cpf' bundle='${labels}'/>"/>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="object.endereco.estado"><fmt:message key="endereco.estado"
                                                                                     bundle="${labels}"/></label>
                                    <select name="object.endereco.estado" id="object.endereco.estado"
                                            class="form-control">
                                        <c:forEach items="${estados}" var="estado">
                                            <option value="${estado.id}"
                                                    <c:if test="${estado.equals(object.endereco.estado)}">selected="selected"</c:if>>${estado.uf}
                                                - ${estado.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label for="object.endereco.cidade"><fmt:message key="endereco.cidade"
                                                                                     bundle="${labels}"/></label>
                                    <input type="text" value="${object.endereco.cidade}" id="object.endereco.cidade"
                                           name="object.endereco.cidade" class="form-control"
                                           placeholder="<fmt:message key='endereco.cidade' bundle='${labels}'/>"/>
                                </div>
                            </div>

                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="object.endereco.cep"><fmt:message key="endereco.cep"
                                                                                  bundle="${labels}"/></label>
                                    <input type="text" value="${object.endereco.cep}" id="object.endereco.cep"
                                           name="object.endereco.cep" class="form-control"
                                           placeholder="<fmt:message key='endereco.cep' bundle='${labels}'/>"/>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-lg-10">
                                <div class="form-group">
                                    <label for="object.endereco.logradouro"><fmt:message key="endereco.logradouro"
                                                                                         bundle="${labels}"/></label>
                                    <input type="text" value="${object.endereco.logradouro}"
                                           id="object.endereco.logradouro"
                                           name="object.endereco.logradouro" class="form-control"
                                           placeholder="<fmt:message key='endereco.logradouro' bundle='${labels}'/>"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="object.endereco.numero"><fmt:message key="endereco.numero"
                                                                                     bundle="${labels}"/></label>
                                    <input type="text" value="${object.endereco.numero}" id="object.endereco.numero"
                                           name="object.endereco.numero" class="form-control"
                                           placeholder="<fmt:message key='endereco.numero' bundle='${labels}'/>"/>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label for="object.endereco.complemento"><fmt:message key="endereco.complemento"
                                                                                          bundle="${labels}"/></label>
                                    <input type="text" value="${object.endereco.complemento}"
                                           id="object.endereco.complemento"
                                           name="object.endereco.complemento" class="form-control"
                                           placeholder="<fmt:message key='endereco.complemento' bundle='${labels}'/>"/>
                                </div>
                            </div>

                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="object.endereco.bairro"><fmt:message key="endereco.bairro"
                                                                                     bundle="${labels}"/></label>
                                    <input type="text" value="${object.endereco.bairro}" id="object.endereco.bairro"
                                           name="object.endereco.bairro" class="form-control"
                                           placeholder="<fmt:message key='endereco.bairro' bundle='${labels}'/>"/>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-sm-5">
                                <div class="form-group">
                                    <label for="object.celular.ddd"><fmt:message key="usuario.celular"
                                                                                 bundle="${labels}"/></label>
                                    <input type="hidden" name="object.celular.id" value="${object.celular.id}"/>

                                    <div class="row">
                                        <div class="col-sm-3">
                                            <input type="text" value="${object.celular.ddd}" id="object.celular.ddd"
                                                   name="object.celular.ddd" class="form-control"
                                                   placeholder="<fmt:message key='telefone.ddd' bundle='${labels}'/>"/>
                                        </div>
                                        <div class="col-sm-5">
                                            <input type="text" value="${object.celular.numero}"
                                                   id="object.celular.numero" name="object.celular.numero"
                                                   class="form-control"
                                                   placeholder="<fmt:message key='telefone.numero' bundle='${labels}'/>"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-5" style="margin-left: 135px;">
                                <div class="form-group">
                                    <label for="object.residencial.ddd"><fmt:message key="usuario.residencial"
                                                                                     bundle="${labels}"/></label>
                                    <input type="hidden" name="object.residencial.id" value="${object.residencial.id}"/>

                                    <div class="row">
                                        <div class="col-sm-3">
                                            <input type="text" value="${object.residencial.ddd}"
                                                   id="object.residencial.ddd" name="object.residencial.ddd"
                                                   class="form-control"
                                                   placeholder="<fmt:message key='telefone.ddd' bundle='${labels}'/>"/>
                                        </div>
                                        <div class="col-sm-5">
                                            <input type="text" value="${object.residencial.numero}"
                                                   id="object.residencial.numero" name="object.residencial.numero"
                                                   class="form-control"
                                                   placeholder="<fmt:message key='telefone.numero' bundle='${labels}'/>"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group" style="margin-left: 15px;">
                                <input type="hidden" id="object.bloqueado" name="object.bloqueado"
                                       value="${object.bloqueado}"/>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="bloqueado" name="bloqueado"
                                           <c:if test="${object.bloqueado}">checked="checked"</c:if>/>Bloqueado
                                </label>
                            </div>

                            <label for="loja" class="control-label"
                                   style="margin-left: 15px;margin-right: 15px;">Lojas</label>

                            <div class="form-group input-group" style="margin-left: 15px;margin-right: 15px;">
                                <input id="loja" type="text" class="form-control" placeholder="Loja">
                <span class="input-group-btn">
                    <button id="pesquisar-loja" class="btn btn-default" type="button">
                        <i class="fa fa-search"></i>
                    </button>
                </span>
                            </div>

                            <div id="lojas" style="display:none;margin-left: 15px;margin-right: 15px;">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="loja.heading" bundle="${labels}"/></th>
                                        <th style="width:5%"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>

                            <h4 style="margin-left: 15px;margin-right: 15px;"><fmt:message key="loja.heading"
                                                                                           bundle="${labels}"/></h4>

                            <div id="lojista" style="margin-left: 15px;margin-right: 15px;">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="loja.heading" bundle="${labels}"/></th>
                                        <th style="width:5%"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${object.lojas}" var="loja">
                                        <tr>
                                            <td>${loja.nome}</td>
                                            <td align="center">
                                                <button type="button" class="btn btn-danger btn-circle"
                                                        loja="${loja.id}" nome="${loja.nome}"
                                                        onclick="javascript:removerLoja(this);">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                        <button type="submit" id="salvar" value="salvar" class="btn btn-default btn-info"
                                style="margin-left: 15px;">
                            <fmt:message key="button.salvar" bundle="${labels}"/></button>


                    </form>
                </div>
            </div>

        </div>

    </div>

</div>
