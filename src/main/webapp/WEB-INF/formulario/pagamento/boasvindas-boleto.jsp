<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3>Parabéns ${franquia.nome}! Você é o mais novo membro da família Ella S/A!</h3>

<div class="well bs-component col-lg-8">
	<fieldset>
		<div class="form-group">
				<div class="col-sm-8">
					Obrigado por sua participação e Seja Bem-Vindo!
				</div>
		</div>
		<div class="form-group">
				<div class="col-sm-10">
					Seu código de fraqueado é ${franquia.codigo} e a sua senha é ${franquia.cleanPassword}.</p>
				</div>
		</div>		
		<c:if test="${not empty tef}">
			<div class="form-group">
				<div class="col-sm-10">
					<a href="${tef}" target="_blank">Clique aqui para pagar</a></p>
				</div>
		</div>
		</c:if>		
	</fieldset>
</div>
<br/>
<c:if test="${not empty boleto }">
<div id="boleto_container">${boleto}</div>
</c:if>