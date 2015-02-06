package com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import com.pmrodrigues.ellasa.sessionscope.Imagens;

import javax.inject.Named;

/**
 * Created by Marceloo on 05/02/2015.
 */
@Resource
public class ImagemController {
    private final Result result;
    private final Validator validation;
    private final Imagens imagens;

    public ImagemController(final Result result, final Validator validation, final Imagens imagens) {
        this.result = result;
        this.validation = validation;
        this.imagens = imagens;
    }

    @Post
    @Path("/imagem/upload")
    public void upload(@Named("arquivo") final UploadedFile arquivo) {
        this.imagens.adicionar(arquivo);
    }

    @Post
    @Path("/imagem/{imagem}/delete")
    public void remover(final String imagem) {
        imagens.remover(imagem);
    }
}
