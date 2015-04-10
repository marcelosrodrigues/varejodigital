<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">
            <jsp:include page="search-formulario.jsp" />
            <li>
                <a class="active" href="<c:url value='/index.do' />"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
            </li>
            <li>
                <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Relatórios<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="flot.html">Flot Charts</a>
                    </li>
                    <li>
                        <a href="morris.html">Morris.js Charts</a>
                    </li>
                </ul>
                <!-- /.nav-second-level -->
            </li>
            <sec:authorize access="hasAnyRole('ADMINISTRADOR','LOJISTA','FINANCEIRO','VENDEDOR')">
            <li>
                <a href="<c:url value='/pedido/index.do' />"><i class="fa fa-table fa-fw"></i> Meus Pedidos</a>
            </li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ADMINISTRADOR','LOJISTA')">
            <li>
                <a href="#"><i class="fa fa-edit fa-fw"></i> Administração<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <sec:authorize access="hasRole('ADMINISTRADOR')">
                        <li>
                            <a href="<c:url value='/loja/index.do' />">Empresas Parceiras</a>
                        </li>
                        <li>
                            <a href="<c:url value='/secao/index.do' />">Departamentos</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ADMINISTRADOR','LOJISTA')">
                        <li>
                            <a href="<c:url value='/produto/index.do' />">Produtos</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ADMINISTRADOR','LOJISTA')">
                    <li>
                        <a href="#">Segurança <span class="fa arrow"></span></a>
                        <ul class="nav nav-third-level collapse">
                            <li>
                                <a href="<c:url value='/usuario/index.do' />">Usuários</a>
                            </li>
                            <sec:authorize access="hasRole('ADMINISTRADOR')">
                                <li>
                                    <a href="<c:url value='/perfil/index.do' />">Grupos</a>
                                </li>
                            </sec:authorize>
                        </ul>
                    </li>
                    </sec:authorize>
                </ul>
            </li>
            </sec:authorize>
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>