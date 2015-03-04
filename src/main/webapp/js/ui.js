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

$('input[id="object.preco"]').maskMoney({decimal: ",", thousands: "."});
$('input[id="object.peso"]').maskMoney({decimal: ",", thousands: "."});


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

$(".checkbox-treeview input[type='checkbox']").each(function () {
    $(this).click(function () {
        var checked = $(this).is(":checked");
        setChecked($(this).val(), checked);
        var father = parseInt($(this).attr("father"));
        if (father > 0 && checked) {
            checarPai(father, checked);
        }
    });
});

function checarPai(pai, checked) {
    $("input[type='checkbox'][value='" + pai + "']").each(function () {
        $(this).prop('checked', checked);
        checarPai(parseInt($(this).attr("father")), checked);
    });
}

function setChecked(pai, checked) {
    $("input[type='checkbox'][father='" + pai + "']").each(function () {
        $(this).prop('checked', checked);
        setChecked($(this).val(), checked);
    });
}

$(function () {

    $('#fileupload').fileupload({
        url: '/imagem/upload',
        dataType: 'json',
        autoUpload: true,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        maxFileSize: 5000000, // 5 MB
        disableImageResize: /Android(?!.*Chrome)|Opera/
            .test(window.navigator.userAgent),
        previewMaxWidth: 100,
        previewMaxHeight: 100,
        previewCrop: true
    }).on('fileuploadadd', function (e, data) {
        data.context = $('<div />').appendTo('#files');
        $.each(data.files, function (index, file) {
            var node = $('<span id="' + file.name + '"/>');
            node.appendTo(data.context).append('<img src="' + URL.createObjectURL(file) + '" width="150px" height="150px"/>');
            node.append($("<br/>")).append($('<label/>').text(file.name))
                .click(function () {

                    var image = $(this).text();

                    $.ajax({
                        type: "POST",
                        url: "/imagem/" + image + "/delete",
                        cache: false
                    });

                    $("div span[id='" + image + "']")
                        .parent()
                        .remove();

                });
        });
    });
});
