$('#dataNascimento').mask("99-99-9999");
$('#dataExpiracao').mask("99-9999");
$('#cpf').mask("999.999.999-99");
$('#cep').mask("99999-999");
$('#cnpj').mask("99.999.999/9999-99");

$('input[id="object.residencial.ddd"]').mask("999");
$('input[id="object.celular.ddd"]').mask("999");

$('input[id="object.residencial.numero"]').mask("9999-9999");
$('input[id="object.celular.numero"]').mask("99999-9999");

$('input[id="object.cpf"]').mask("999.999.999-99");
$('input[id="object.endereco.cep"]').mask("99999-999");
$('input[id="object.cnpj"]').mask("99.999.999/9999-99");


$("#dataNascimento").datepicker({
    dateFormat: "dd-mm-yyyy"
});

$("#dataExpiracao").datepicker({
    dateFormat: "mm-yyyy"
});

$(document).tooltip();

$("#bloqueado").click(function () {
    $("input[id='object.bloqueado']").val($("#bloqueado").is(':checked'));
});

$(document).ready(function () {
    $('.tree li').each(function () {
        if ($(this).children('ul').length > 0) {
            $(this).addClass('parent');
        }
    });

    $('.tree  li.parent > a').click(function () {
        $(this).parent().toggleClass('active');
        $(this).parent().children('ul').slideToggle('fast');
    });

});


