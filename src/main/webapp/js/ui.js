$('#dataNascimento').mask("99-99-9999");
$('#dataExpiracao').mask("99-9999");
$('#cpf').mask("999.999.999-99");
$('#cep').mask("99999-999");
$('#cnpj').mask("99.999.999/9999-99");

$( "#dataNascimento" ).datepicker({   
	dateFormat: "dd-mm-yyyy"
	});

$( "#dataExpiracao" ).datepicker({   
	dateFormat: "mm-yyyy"
	});

$( document ).tooltip();
