package com.pmrodrigues.ellasa.sessionscope;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.pmrodrigues.ellasa.Constante;
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

    private final Collection<String> fileName = new HashSet<>();
    private final ResourceBundle bundle = ResourceBundle.getBundle("upload");
    private static final Logger logging = Logger.getLogger(Imagens.class);

    public void adicionar(final UploadedFile imagem) {

        try {

            logging.debug(format("iniciando o upload da imagem %s", imagem.getFileName()));

            final File image = new File(bundle.getString(Constante.IMAGE_PATH) + imagem.getFileName());
            criar(image);

            FileUtils.copyInputStreamToFile(imagem.getFile(), image);

            fileName.add(imagem.getFileName());

            logging.debug(format("termino do upload da imagem %s", imagem.getFileName()));

        } catch (IOException e) {
            logging.fatal("erro ao salvar o arquivo " + e.getMessage(), e);
        }
    }


    private void criar(File image) throws IOException {
        if (image.exists()) {
            image.delete();
        }
        image.createNewFile();
    }

    public void remover(String imagem) {

        final File image = new File(bundle.getString(Constante.IMAGE_PATH) + imagem);
        if (image.exists()) {
            image.delete();
        }

        this.fileName.remove(imagem);

    }

    public Collection<String> getArquivos() {
        return fileName;
    }

    public void apagar() {
        this.fileName.clear();
    }
}
