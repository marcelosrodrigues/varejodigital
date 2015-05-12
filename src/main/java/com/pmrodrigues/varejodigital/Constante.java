package com.pmrodrigues.varejodigital;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public interface Constante { //NOPMD

    String LISTA_ESTADOS = "estados";

    String SUCESSO = "sucesso";

    String RESULT_LIST = "resultlist";

    String OBJECT = "object";

    Integer TAMANHO_PAGINA = 20;

    String DEPARTAMENTOS = "departamentos";

    String IMAGE_PATH = "image_path";

    String LOJAS = "lojas";

    String URL_IMAGENS = ResourceBundle.getBundle("configuracao").getString(IMAGE_PATH);

    Locale PT_BR = new Locale("pt-BR");

    Date DATA_INICIAL = new DateTime(1900, 1, 1, 0, 0).toDate();

    String NUMERO_MAXIMO_TENTATIVAS_FALHAS_FIELD = "numero_maximo_tentativas_falhas";

    Long NUMERO_MAXIMO_TENTATIVAS_FALHAS = Long.parseLong(ResourceBundle.getBundle("configuracao").getString(NUMERO_MAXIMO_TENTATIVAS_FALHAS_FIELD));
}
