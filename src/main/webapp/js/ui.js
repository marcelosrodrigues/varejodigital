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
        url: '/imagem/upload.json',
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
            node.appendTo(data.context)
                .append('<img src="' + URL.createObjectURL(file) + '" width="150px" height="150px"/>');

            node.append($("<br/>"))
                .append($('<label/>')
                    .text(file.name))
                .click(function () {
                    var image = $(this).text();

                    $.ajax({
                        type: "POST",
                        url: "/imagem/" + image + "/delete.json",
                        cache: false
                    });

                    $("div span[id='" + image + "']")
                        .parent()
                        .remove();
                });
        });
    });
});

$(function () {

    $('#icone').fileupload({
        url: '/imagem/upload.json',
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
            node.appendTo(data.context)
                .append('<img src="' + URL.createObjectURL(file) + '" width="150px" height="150px"/>');

            node.append($("<br/>"))
                .append($('<label/>')
                    .text(file.name))
                .click(function () {
                    var image = $(this).text();

                    $.ajax({
                        type: "POST",
                        url: "/imagem/" + image + "/delete.json",
                        cache: false
                    });

                    $("div span[id='" + image + "']")
                        .parent()
                        .remove();
                });
        });
    });
});

function deletarImagem(image) {
    $.ajax({
        type: "POST",
        url: "/imagem/" + image + "/remove.json",
        cache: false
    });

    $("div span[id='" + image + "']")
        .parent()
        .remove();
}

function deletarIcone(image) {
    $.ajax({
        type: "POST",
        url: "/secao/" + image + "/remover/icone.json",
        cache: false
    });

    $("div span[id='" + image + "']")
        .parent()
        .remove();
}


function montatabeladepartamentos(data) {

    $("#departamentos > table > tbody").find("tr").remove();

    $.each(data.list, function (index, element) {

        $("#departamentos > table > tbody").append("<tr><td>"
        + element.id + "</td>"
        + "<td>" + element.nome + "</td>"
        + "<td align=\"center\"><button id=\"subsecoes\" onclick=\"javacript:listarSubSecoes(this)\" type=\"button\" class=\"btn btn-info btn-circle\" secao=\"" + element.id + "\" nome=\"" + element.nome + "\">"
        + "<i class=\"fa fa-check\"></i></button></td></tr>");
        $("#departamentos").show();

    });
}
$("#pesquisar").click(function () {

    var loja = document.getElementById("object.loja");

    $.getJSON(
        "/" + $(loja).val() + "/secoes/" + $("#departamento").val() + "/list.json", montatabeladepartamentos);

});

$("#pesquisar-usuario").click(function () {
    $.getJSON(
        "/usuarios/" + $("#usuario").val() + "/list.json", montartabelausuario);
});

function listarSubSecoes(secao) {

    var loja = document.getElementById("object.loja");

    $.getJSON("/" + $(loja).val() + "/secoes/" + $(secao).attr("secao") + "/filhos/list.json", function (data) {

        if (data.list.length == 0) {
            $("input[id='object.secao']").val($(secao).attr("secao"));
            $("#departamento").val($(secao).attr("nome"));
            $("#departamentos > table > tbody").find("tr").remove();
            $("#departamentos").hide();
        } else {
            montatabeladepartamentos(data);
        }

    });
}

$("#adicionar-tamanho").click(function () {

    $.ajax({
        url: "/produto/tamanho/" + $("#tamanho").val() + "/adicionar.json",
        type: "POST",
        cache: false
    }).done(montartabelatamanho);

});

function montartabelausuario(data) {

    $("#usuarios > table > tbody").find("tr").remove();

    $.each(data.list, function (index, element) {

        $("#usuarios > table > tbody").append("<tr>"
        + "<td>" + element.nomeCompleto + "</td>"
        + "<td>" + element.email + "</td>"
        + "<td align=\"center\">"
        + "<button id=\"adicionar-usuario\" type=\"button\" class=\"btn btn-info btn-circle\" email=\"" + element.email + "\" usuario=\"" + element.id + "\" nome=\"" + element.nomeCompleto + "\">"
        + "<i class=\"fa fa-check\"></i></button></td></tr>");

    });

    $("#adicionar-usuario").each(function (index) {
        $(this).click(function () {

            $.ajax({
                url: "/grupo/" + $(this).attr("usuario") + "/adicionar.json",
                type: "POST",
                cache: false
            }).done(adicionartabelamembros);

        });
    });

    $("#usuarios").show();
}

function adicionartabelamembros(data) {

    $("#membros > table > tbody").append("<tr>"
    + "<td>" + data.usuario.nomeCompleto + "</td>"
    + "<td>" + data.usuario.email + "</td>"
    + "<td align=\"center\">"
    + "<button id=\"remover-usuario\" type=\"button\" class=\"btn btn-danger btn-circle\" usuario=\"" + data.usuario.id + "\"  onclick=\"javascript:removerUsuario(this);\">"
    + "<i class=\"fa fa-times\"></i></button></td></tr>");

}

function montartabelatamanho(data) {

    $("#tamanhos > table > tbody").find("tr").remove();

    $.each(data.list, function (index, element) {

        $("#tamanhos > table > tbody").append("<tr>"
        + "<td>" + element.descricao + "</td>"
        + "<td align=\"center\">"
        + "<button id=\"remover-tamanho\" type=\"button\" class=\"btn btn-danger btn-circle\" tamanho=\"" + element.id + "\" nome=\"" + element.descricao + "\">"
        + "<i class=\"fa fa-times\"></i></button></td></tr>");

    });

    $("#remover-tamanho").each(function (index) {
        $(this).click(function () {

            $.ajax({
                url: "/produto/tamanho/" + $(this).attr("nome") + "/remover.json",
                type: "POST",
                cache: false
            }).done(montartabelatamanho);

        });
    });

    $("#tamanhos").show();
}

function removerTamanho(tamanho) {
    $.ajax({
        url: "/produto/tamanho/" + $(tamanho).attr("nome") + "/" + $(tamanho).attr("tamanho") + "/remover.json",
        type: "POST",
        cache: false
    }).done(function () {
        $(tamanho).parent()
            .parent()
            .remove();
    });
}

function removerUsuario(usuario) {
    $.ajax({
        url: "/grupo/" + $(usuario).attr("usuario") + "/remover.json",
        type: "POST",
        cache: false
    }).done(function () {
        $(usuario).parent()
            .parent()
            .remove();
    });
}

function removerProduto(value) {
    var remove = confirm("Confirma a exclus\00C3o do produto ?");
    if (remove) {
        $("form").attr("action", "/produto/excluir.do?object.id=" + value);
        $("form").attr("method", "POST");
        $("form").submit();
    }
}
