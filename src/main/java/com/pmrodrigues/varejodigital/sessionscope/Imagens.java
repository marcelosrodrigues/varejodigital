package com.pmrodrigues.varejodigital.sessionscope;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.models.Imagem;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;

import static java.lang.String.format;

/**
 * Created by Marceloo on 05/02/2015.
 */
@Component
@SessionScoped
public class Imagens {

    private static final Logger logging = Logger.getLogger(Imagens.class);
    private final Collection<Imagem> imagens = new HashSet<>();
    private final ResourceBundle bundle = ResourceBundle.getBundle("upload");

    public void adicionar(final UploadedFile imagem) {

        try {

            logging.debug(format("iniciando o upload da imagem %s", imagem.getFileName()));

            final File image = new File(bundle.getString(Constante.IMAGE_PATH) + imagem.getFileName());
            criar(image);

            FileUtils.copyInputStreamToFile(imagem.getFile(), image);

            imagens.add(new Imagem(imagem.getFileName()));

            logging.debug(format("termino do upload da imagem %s", imagem.getFileName()));

        } catch (IOException e) {
            logging.fatal("erro ao salvar o arquivo " + e.getMessage(), e);
        }
    }


    private void criar(final File image) throws IOException {
        if (image.exists()) {
            image.delete();
        }
        image.createNewFile();
    }

    public void remover(final String imagem) {

        final File image = new File(bundle.getString(Constante.IMAGE_PATH) + imagem);
        if (image.exists()) {
            image.delete();
        }

        this.imagens.remove(new Imagem(imagem));

    }

    public Collection<Imagem> getImagens() {
        return imagens;
    }

    public void apagar() {
        this.imagens.clear();
    }
}
