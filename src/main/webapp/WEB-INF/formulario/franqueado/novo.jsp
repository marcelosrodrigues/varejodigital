<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


 <form method="post" class="form-horizontal" action="seja-um-franqueado.html">
	
	<div class="form-group">
		<label for="franqueado.nomeCompleto" class="col-sm-2 control-label">Nome</label>
		<div class="col-sm-8">
			<input type="text" name="franqueado.nomeCompleto" id="franqueado.nomeCompleto" class="form-control  input-sm" />
		</div>
	</div>
	
	
	<div class="form-group">
		<label for="franqueado.cpf" class="col-sm-2 control-label">CPF</label>
		<div class="col-sm-2">
			<input type="text" name="franqueado.cpf" id="franqueado.cpf"  class="form-control  input-sm"/>
		</div>
	</div>
	
	
	<div class="form-group">
		<label for="franqueado.email" class="col-sm-2 control-label">E-mail</label>
		<div class="col-sm-8">
			<input type="text" name="franqueado.email" id="franqueado.email"  class="form-control  input-sm"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="franqueado.dataNascimento" class="col-sm-2 control-label">Data de Nascimento</label>
		<div class="col-sm-2">
			<input type="text" name="franqueado.dataNascimento" id="franqueado.dataNascimento" class="form-control  input-sm" />
		</div>
	</div>
	
	<div class="form-group">
		<label for="franqueado.endereco.cep" class="col-sm-2 control-label">CEP</label>
		<div class="col-sm-2">
			<input type="text" name="franqueado.endereco.cep" id="franqueado.endereco.cep"  class="form-control  input-sm"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="franqueado.endereco.estado" class="col-sm-2 control-label">Estado</label>
			<div class="col-sm-2">
				<select name="franqueado.endereco.estado" id="franqueado.endereco.estado"  class="form-control  input-sm">
					<option />
					<c:forEach items="${estados}" var="estado" >
						<option value="${estado.uf}">${estado.nome}</option>
					</c:forEach>
				</select>
			</div>
	</div>
	
	<div class="form-group">
		<label for="franqueado.endereco.cidade" class="col-sm-2 control-label">Cidade</label>
		<div class="col-sm-8">
			<input type="text" name="franqueado.endereco.cidade" id="franqueado.endereco.cidade"  class="form-control  input-sm"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="franqueado.endereco.bairro" class="col-sm-2 control-label">Bairro</label>
		<div class="col-sm-8">
			<input type="text" name="franqueado.endereco.bairro" id="franqueado.endereco.bairro"  class="form-control  input-sm"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="franqueado.endereco.logradouro" class="col-sm-2 control-label">Logradouro</label>
		<div class="col-sm-8">
			<input type="text" name="franqueado.endereco.Logradouro" id="franqueado.endereco.Logradouro"  class="form-control  input-sm"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="franqueado.endereco.numero" class="col-sm-2 control-label">Numero</label>
		<div class="col-sm-2">
			<input type="text" name="franqueado.endereco.numero" id="franqueado.endereco.numero"  class="form-control  input-sm"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="franqueado.endereco.complemento" class="col-sm-2 control-label">Complemento</label>
		<div class="col-sm-2">
			<input type="text" name="franqueado.endereco.complemento" id="franqueado.endereco.complemento"  class="form-control  input-sm"/>
		</div>
	</div>
	
	<div class="form-group">
		<label for="indicacao" class="col-sm-2 control-label">Qual programa?</label>
		<c:forEach items="${franquias}" var="franquia">
			<label class="checkbox-inline">			
  				<input type="radio" id="franquia" name="franquia" value="${franquia.id}" title="${franquia.descricao}"> 
  				<label  title="${franquia.descricao}">${franquia.nome}</label>
			</label>
		</c:forEach>		
	</div>
	
	
	<div class="form-group">
		<label for="indicacao" class="col-sm-2 control-label">Código do franqueado que te indicou</label>
		<div class="col-sm-2">
			<input type="text" name="indicacao" id="indicacao"  class="form-control  input-sm" />
		</div>
	</div>
	
	
	<input type="submit" value="Salvar" class="btn btn-default bottom" />
	
</form>