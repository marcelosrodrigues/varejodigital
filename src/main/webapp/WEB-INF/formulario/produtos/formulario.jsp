<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pmrodrigues" uri="/WEB-INF/pmrodrigues.tld" %>
<fmt:setBundle basename="labels" scope="application" var="labels"/>


<div class="col-lg-12">

    <div class="panel panel-default">

        <div class="panel-heading">
            <fmt:message key="produto.heading" bundle="${labels}"/>
        </div>

        <div class="panel-body">

            <pmrodrigues:summary text="Ocorreu um erro na validação dos seus dados"/>

            <div class="row">
                <div class="col-lg-10">
                    <form action="<c:url value='/produto/salvar.do' />" method="post" role="form"
                          enctype="multipart/form-data">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="object.id" value="${object.id}"/>

                        <pmrodrigues:combobox label="loja.heading" errorField="loja" list="${lojas}" id="object.loja"
                                              valueField="id" labelField="nome" value="${object.loja}"/>

                        <pmrodrigues:textfield label="produto.nome" value="${object.nome}" id="object.nome"
                                               errorField="nome"/>

                        <pmrodrigues:textfield label="produto.preco" value="${object.preco}" id="object.preco"
                                               errorField="preco"/>

                        <pmrodrigues:textfield label="produto.peso" value="${object.peso}" id="object.peso"
                                               errorField="peso"/>


                        <input type="hidden" name="object.secao" id="object.secao" value="${object.secao.id}"/>

                        <label for="departamento" class="control-label">Departamento</label>

                        <div class="form-group input-group">
                            <input id="departamento" type="text" class="form-control" placeholder="Departamento"
                                   value="${object.secao.nome}">
                            <span class="input-group-btn">
                                <button id="pesquisar" class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>

                        <div id="departamentos" style="display:none">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th style="width:10%">#</th>
                                    <th>Departamento</th>
                                    <th style="width:5%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                        <pmrodrigues:textarea label="produto.descricaoBreve" value="${object.descricaoBreve}"
                                              id="object.descricaoBreve" linhas="3" errorField="descricaoBreve"/>

                        <pmrodrigues:textarea label="produto.descricao" value="${object.descricao}"
                                              id="object.descricao" linhas="6" errorField="descricao"/>

                        <label for="tamanho" class="control-label">Tamanho</label>

                        <div class="form-group input-group">
                            <input id="tamanho" type="text" class="form-control" placeholder="Tamanho">
                            <span class="input-group-btn">
                                <button id="adicionar-tamanho" class="btn btn-default" type="button">
                                    <i class="fa fa-check"></i>
                                </button>
                            </span>
                        </div>

                        <div id="tamanhos">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Tamanho</th>
                                    <th style="width:5%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${object.atributos}" var="atributo">
                                    <tr>
                                        <td>${atributo.descricao}</td>
                                        <td align="center">
                                            <button type="button" class="btn btn-danger btn-circle"
                                                    tamanho="${atributo.id}" nome="${atributo.descricao}"
                                                    onclick="javascript:removerTamanho(this);">
                                                <i class="fa fa-times"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="form-group">
                            <label>Imagens do produto</label>
                            <input type="file" name="arquivo" id="fileupload"/>
                        </div>
                        <div id="files">
                            <pmrodrigues:imagem
                                    imagens="${object.imagens}"/>
                        </div>

                        <button type="submit" value="salvar" class="btn btn-default btn-info">
                            <fmt:message key="button.salvar" bundle="${labels}"/></button>

                    </form>
                </div>
            </div>

        </div>

    </div>

</div>