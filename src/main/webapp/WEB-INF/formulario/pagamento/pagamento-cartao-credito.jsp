<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="well bs-component col-lg-8">
	<form method="post" class="form-horizontal" action="pagar-assinatura.html">
		<fieldset>
		
		
			<input type="hidden" name="meioPagamento" value="${ordempagamento.meioPagamento.id}" />
			<input type="hidden" name="tipoFranquia" value="${ordempagamento.contrato.tipoFranquia.id}" />			
			<input type="hidden" name="ordempagamento.contrato.franqueado.nomeCompleto"  value="${ordempagamento.contrato.franqueado.nomeCompleto}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.CPF"  value="${ordempagamento.contrato.franqueado.CPF}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.email"  value="${ordempagamento.contrato.franqueado.email}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.residencial.ddd" value="${ordempagamento.contrato.franqueado.residencial.ddd}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.residencial.numero" value="${ordempagamento.contrato.franqueado.residencial.numero}" />
			
			<c:if test="${ not empty ordempagamento.contrato.franqueado.celular.ddd  &&  not empty ordempagamento.contrato.franqueado.celular.numero}" >
				<input type="hidden" name="ordempagamento.contrato.franqueado.celular.ddd" value="${ordempagamento.contrato.franqueado.celular.ddd}" />
				<input type="hidden" name="ordempagamento.contrato.franqueado.celular.numero" value="${ordempagamento.contrato.franqueado.celular.numero}" />
			</c:if>
			
			<fmt:formatDate value="${ordempagamento.contrato.franqueado.dataNascimento}" pattern="dd-MM-yyyy" var="dataNascimento" type="date"  timeZone="pt-BR"/>
			
			<input type="hidden" name="ordempagamento.contrato.franqueado.dataNascimento"  value="${dataNascimento}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.endereco.cep"  value="${ordempagamento.contrato.franqueado.endereco.cep}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.endereco.estado.uf"  value="${ordempagamento.contrato.franqueado.endereco.estado.uf}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.endereco.cidade"  value="${ordempagamento.contrato.franqueado.endereco.cidade}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.endereco.bairro"  value="${ordempagamento.contrato.franqueado.endereco.bairro}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.endereco.logradouro"  value="${ordempagamento.contrato.franqueado.endereco.logradouro}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.endereco.numero"  value="${ordempagamento.contrato.franqueado.endereco.numero}" />
			<input type="hidden" name="ordempagamento.contrato.franqueado.endereco.complemento"  value="${ordempagamento.contrato.franqueado.endereco.complemento}" />
		
			<div class="form-group">
				<label class="col-sm-3 control-label">Nome</label>
				<div class="col-sm-8">
					<label class="control-label">${ordempagamento.contrato.franqueado.nomeCompleto}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label">CPF</label>
				<div class="col-sm-8">
					<label class="control-label">${ordempagamento.contrato.franqueado.CPF}</label>
				</div>
			</div>
			
			<div class="form-group">
				<label for="ordempagamento.numero"
					class="col-sm-3 control-label">Número</label>
				<div class="col-sm-6">
					<input type="text" name="ordempagamento.numero"
						id="ordempagamento.numero" class="form-control  input-sm" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="ordempagamento.codigoSeguranca"
					class="col-sm-3 control-label">CVV</label>
				<div class="col-sm-2">
					<input type="text" name="ordempagamento.codigoSeguranca"
						id="ordempagamento.codigoSeguranca" class="form-control  input-sm" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="ordempagamento.dataExpiracao"
					class="col-sm-3 control-label">Data de Expiração</label>
				<div class="col-sm-2">
					<input type="text" name="ordempagamento.dataExpiracao"
						id="dataExpiracao" class="form-control  input-sm" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="ordempagamento.portador"
					class="col-sm-3 control-label">Nome do portador</label>
				<div class="col-sm-8">
					<input type="text" name="ordempagamento.portador"
						id="ordempagamento.portador" class="form-control  input-sm" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="ordempagamento.CPF"
					class="col-sm-3 control-label">CPF do Portador</label>
				<div class="col-sm-3">
					<input type="text" name="ordempagamento.CPF"
						id="cpf" class="form-control  input-sm" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="ordempagamento.telefone"
					class="col-sm-3 control-label">Telefone do Portador</label>
				<div class="col-sm-3">
					<input type="text" name="ordempagamento.telefone"
						id="cpf" class="form-control  input-sm" />
				</div>
			</div>
		
			<input type="submit" value="Concluir" class="btn btn-primary" />
		</fieldset>
	</form>
</div>	