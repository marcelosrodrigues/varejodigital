package com.pmrodrigues.varejodigital.taglib;

import com.pmrodrigues.varejodigital.models.Imagem;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Collection;

import static java.lang.String.format;

/**
 * Created by Marceloo on 04/03/2015.
 */
public class ListagemImagensTag extends SimpleTagSupport {

    private Collection<Imagem> imagens;

    public void setImagens(final Collection<Imagem> imagens) {
        this.imagens = imagens;
    }

    @Override
    public void doTag() throws JspException, IOException {
        final JspWriter writer = this.getJspContext().getOut();

        for (final Imagem imagem : imagens) {
            final String arquivo = imagem.getUrl().substring(imagem.getUrl().lastIndexOf("/") + 1);
            writer.print(
                    format("<div><span id=\"%s\"><img src=\"%s\" width=\"150px\" height=\"150px\"/><br/><label onclick=\"javascript:deletarImagem(%s);\">%s</label></span></div>",
                            imagem.getId(),
                            imagem.getUrl(),
                            imagem.getId(),
                            arquivo)
            );

        }
    }
}
