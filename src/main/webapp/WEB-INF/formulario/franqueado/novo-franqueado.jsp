<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="well bs-component col-lg-8">

	<c:if test="${not empty errors}">
		<div class="alert alert-dismissable alert-danger">
			<buttOn type="button" class="close" data-dismiss="alert">�</button>
			<strong>Ocorreu um erro na valida��o dos seus dados</strong><br/>
			<c:forEach items="${errors}" var="error">
				<p>${error.message}</p>
			</c:forEach>
		</div>
	</c:if>

	<form method="post" class="form-horizontal"
		action="seja-um-franqueado.html">

		<fieldset>
	
		    <div class="form-group">
				<label for="franqueado.nomeCompleto" class="col-sm-3 control-label">Nome</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.nomeCompleto"
						id="franqueado.nomeCompleto" class="form-control  input-sm"
						maxlength="250" placeholder="Nome completo" />
				</div>
			</div>


			<div class="form-group">
				<label for="franqueado.cpf" class="col-sm-3 control-label">CPF</label>
				<div class="col-sm-3">
					<input type="text" name="franqueado.CPF" id="cpf"
						class="form-control  input-sm" maxlength="14" placeholder="CPF" />
				</div>
			</div>


			<div class="form-group">
				<label for="franqueado.email" class="col-sm-3 control-label">E-mail</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.email" id="franqueado.email"
						class="form-control  input-sm" maxlength="250" placeholder="E-mail" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.dataNascimento"
					class="col-sm-3 control-label">Data de Nascimento</label>
				<div class="col-sm-2">
					<input type="text" name="franqueado.dataNascimento"
						id="dataNascimento" class="form-control  input-sm" maxlength="10" placeholder="Data de nascimento"/>
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.cep" class="col-sm-3 control-label">CEP</label>
				<div class="col-sm-3 ">
					<input type="text" name="franqueado.endereco.cep" id="cep"
						class="form-control  input-sm" maxlength="9" placeholder="CEP" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.estado"
					class="col-sm-3 control-label">Estado</label>
				<div class="col-sm-4">
					<select name="franqueado.endereco.estado.uf"
						id="franqueado.endereco.estado.uf" class="form-control  input-sm" >
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
						id="franqueado.endereco.cidade" class="form-control  input-sm"
						maxlength="200" placeholder="Cidade" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.bairro"
					class="col-sm-3 control-label">Bairro</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.endereco.bairro"
						id="franqueado.endereco.bairro" class="form-control  input-sm"
						maxlength="200" placeholder="Bairro" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.logradouro"
					class="col-sm-3 control-label">Logradouro</label>
				<div class="col-sm-8">
					<input type="text" name="franqueado.endereco.logradouro"
						id="franqueado.endereco.logradouro" class="form-control  input-sm"
						maxlength="200" placeholder="Logradouro" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.numero"
					class="col-sm-3 control-label">N�mero</label>
				<div class="col-sm-2">
					<input type="text" name="franqueado.endereco.numero"
						id="franqueado.endereco.numero" class="form-control  input-sm"
						maxlength="10"  placeholder="N�mero"/>
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.endereco.complemento"
					class="col-sm-3 control-label">Complemento</label>
				<div class="col-sm-2">
					<input type="text" name="franqueado.endereco.complemento"
						id="franqueado.endereco.complemento"
						class="form-control  input-sm" maxlength="10" placeholder="Complemento" />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.residencial.ddd"
					class="col-sm-3 control-label">Telefone 1</label>
				<div class="col-sm-2">
					<input type="text" id="franqueado.residencial.ddd" name="franqueado.residencial.ddd"
						class="form-control input-sm-2" maxlength="2" placeholder="DDD" />
				</div>
				<div class="col-sm-4">
					<input type="text" id="franqueado.residencial.numero" name="franqueado.residencial.numero"
						class="form-control input-sm-2" maxlength="8"  placeholder="Telefone"  />
				</div>
			</div>

			<div class="form-group">
				<label for="franqueado.celular.ddd" class="col-sm-3 control-label">Telefone 2</label>
				<div class="col-sm-2">
					<input type="text" id="franqueado.celular.ddd" name="franqueado.celular.ddd"
						class="form-control input-sm-2" maxlength="2"  placeholder="DDD" />
				</div>
				<div class="col-sm-4">
					<input type="text" id="franqueado.celular.numero" name="franqueado.celular.numero"
						class="form-control input-sm-2" maxlength="8"  placeholder="Telefone" />
				</div>
			</div>

			<div class="form-group">
				<label for="franquia" class="col-sm-3 control-label">Qual
					programa?</label>
				<c:forEach items="${franquias}" var="franquia">
					<label class="checkbox-inline"> <input type="radio"
						id="franquia" name="franquia" value="${franquia.id}"
						title="${franquia.descricao}"> <label
						title="${franquia.descricao}">${franquia.nome}</label>
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
						class="form-control  input-sm" placeholder="C�digo do franqueado" />
				</div>
			</div>


			<input id="avancar" type="submit" value="Avan�ar" class="btn btn-primary" />
		</fieldset>
	</form>
</div>