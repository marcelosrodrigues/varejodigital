package com.pmrodrigues.ellasa;

import java.util.Locale;
import java.util.ResourceBundle;

public interface Constante {

    int QUANTIDADE_MAXIMA_DE_FRANQUEADOS = 5;

    String LISTA_ESTADOS = "estados";

    String LISTA_FRANQUIAS = "franquias";

    String LISTA_MEIOS_PAGAMENTO = "meiosdepagamento";

    String FRANQUEADO = "franquia";

    String ORDEM_PAGAMENTO = "ordempagamento";

    String BOLETO = "boleto";

    String TEF = "tef";

    String SUCESSO = "sucesso";

    String RESULT_LIST = "resultlist";

    String OBJECT = "object";

    Integer TAMANHO_PAGINA = 20;

    String USUARIO = "usuario";

    String DEPARTAMENTOS = "departamentos";

    String IMAGE_PATH = "image_path";

    String LOJAS = "lojas";

    String URL_IMAGENS = ResourceBundle.getBundle("configuracao").getString(IMAGE_PATH);

    Locale PT_BR = new Locale("pt-BR");
}
