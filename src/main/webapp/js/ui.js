$("input[id='franqueado.dataNascimento']").mask("99-99-9999");
$("input[id='franqueado.cpf']").mask("999.999.999-99");
$("input[id='franqueado.endereco.cep']").mask("99999-99");

$( "input[id='franqueado.dataNascimento']" ).datepicker({    	
	changeMonth: true,
    changeYear: true,
	dateFormat: "dd-mm-yy"
	});

$( "input[id='franquia']" ).each(function(key,data) {
	$(data).tooltip({
	    track: true
	});
});