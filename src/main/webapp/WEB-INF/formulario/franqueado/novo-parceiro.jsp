<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="well bs-component col-lg-8">

	<c:if test="${not empty errors}">
		<div class="alert alert-dismissable alert-danger">
			<buttOn type="button" class="close" data-dismiss="alert">×</button>
			<strong>Ocorreu um erro na validação dos seus dados</strong><br/>
			<c:forEach items="${errors}" var="error">
				<p>${error.message}</p>
			</c:forEach>
		</div>
	</c:if>

	<form method="post" class="form-horizontal"
		action="seja-um-parceiro.html">

		<fieldset>

			<div class="form-group">
				<label for="franqueado.razaoSocial" class="col-sm-3 control-label">Razão Social</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.razaoSocial"
						id="franqueado.nomeCompleto" class="form-control  input-sm" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="franqueado.nomeFantasia" class="col-sm-3 control-label">Nome fantasia</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.nomeFantasia"
						id="franqueado.nomeFantasia" class="form-control  input-sm" />
				</div>
			</div>


			<div class="form-group">
				<label for="franqueado.CNPJ" class="col-sm-3 control-label">CNPJ</label>
				<div class="col-sm-5">
					<input type="text" name="franqueado.CNPJ" id="cnpj"
						class="form-control  input-sm" />
				</div>
			</div>


			<div class="form-group">
				<label for="franqueado.email" class="col-sm-3 control-label">E-mail</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.email" id="franqueado.email"
						class="form-control  input-sm" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.cep" class="col-sm-3 control-label">CEP</label>
				<div class="col-sm-3 ">
					<input type="text" name="franqueado.endereco.cep" id="cep"
						class="form-control  input-sm" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.estado"
					class="col-sm-3 control-label">Estado</label>
				<div class="col-sm-4">
					<select name="franqueado.endereco.estado.uf"
						id="franqueado.endereco.estado.uf" class="form-control  input-sm">
						<option />
						<c:forEach items="${estados}" var="estado">
							<option value="${estado.uf}">${estado.nome}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.cidade"
					class="col-sm-3 control-label">Cidade</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.endereco.cidade"
						id="franqueado.endereco.cidade" class="form-control  input-sm" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.bairro"
					class="col-sm-3 control-label">Bairro</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.endereco.bairro"
						id="franqueado.endereco.bairro" class="form-control  input-sm" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.logradouro"
					class="col-sm-3 control-label">Logradouro</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.endereco.logradouro"
						id="franqueado.endereco.logradouro" class="form-control  input-sm" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.numero"
					class="col-sm-3 control-label">Numero</label>
				<div class="col-sm-2">
					<input type="text" name="franqueado.endereco.numero"
						id="franqueado.endereco.numero" class="form-control  input-sm" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.complemento"
					class="col-sm-3 control-label">Complemento</label>
				<div class="col-sm-2">
					<input type="text" name="franqueado.endereco.complemento"
						id="franqueado.endereco.complemento"
						class="form-control  input-sm" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="franqueado.residencial.ddd"
					class="col-sm-3 control-label">Telefone</label>
				<div class="col-sm-2">
					<input type="text" name="franqueado.residencial.ddd"
						class="form-control input-sm-2" />
				</div>
				<div class="col-sm-4">
					<input type="text" name="franqueado.residencial.numero"
						class="form-control input-sm-2" />	
				</div>
			</div>
			
			<div class="form-group">
				<label for="franqueado.celular.ddd"
					class="col-sm-3 control-label">Celular</label>
				<div class="col-sm-2">
					<input type="text" name="franqueado.celular.ddd"
						class="form-control input-sm-2" />
				</div>
				<div class="col-sm-4">
					<input type="text" name="franqueado.celular.numero"
						class="form-control input-sm-2" />	
				</div>
			</div>

			<div class="form-group">
				<label for="indicacao" class="col-sm-3 control-label">Qual
					programa?</label>
				<c:forEach items="${franquias}" var="franquia">
					<label class="checkbox-inline"> <input type="radio"
						id="franquia" name="franquia" value="${franquia.id}"
						title="${franquia.descricao}" onfocus="javascript:tooltip(this);">
						<label title="${franquia.descricao}"
						onfocus="javascript:tooltip(this);">${franquia.nome}</label>
					</label>
				</c:forEach>
			</div>

			<div class="form-group">
				<label for="meiodepagamento" class="col-sm-3 control-label">Formas
					de pagamento</label>
				<div class="col-sm-3">
					<select name="meiodepagamento" id="meiodepagamento"
						class="form-control  input-sm">
						<option />
						<c:forEach items="${meiosdepagamento}" var="meiodepagamento">
							<option value="${meiodepagamento.id}">${meiodepagamento.descricao}</option>
						</c:forEach>
					</select>
				</div>

			</div>


			<div class="form-group">
				<label for="indicacao" class="col-sm-3 control-label">Indicado
					por</label>
				<div class="col-sm-2">
					<input type="text" name="indicacao" id="indicacao"
						class="form-control  input-sm" />
				</div>
			</div>


			<input type="submit" value="Avançar" class="btn btn-primary" />
		</fieldset>
	</form>
</div>