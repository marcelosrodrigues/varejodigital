$('#dataNascimento').mask("99-99-9999");
$('#cpf').mask("999.999.999-99");
$('#cep').mask("99999-999");

$( "#dataNascimento" ).datepicker({   
	dateFormat: "dd-mm-yyyy"
	});

$( "#dataExpiracao" ).datepicker({   
	dateFormat: "mm-yyyy"
	});

function tooltip(field) {
	$(field).tooltip({
	    track: true
	});
}
