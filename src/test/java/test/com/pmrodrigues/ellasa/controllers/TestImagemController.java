package test.com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.interceptor.multipart.DefaultUploadedFile;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.controllers.ImagemController;
import com.pmrodrigues.ellasa.sessionscope.Imagens;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Marceloo on 05/02/2015.
 */
public class TestImagemController {

    private final MockResult result = new MockResult();
    private final MockValidator validation = new MockValidator();
    private final ResourceBundle bundle = ResourceBundle.getBundle("upload");
    private final File imagem = new File(bundle.getString("image_path") + "/loading.gif");
    private final String FILE_NAME = System.getProperty("user.dir") + "/src/test/resources/loading.gif";

    @Test
    public void subirArquivo() throws IOException {
        final Imagens imagens = new Imagens();
        final ImagemController controller = new ImagemController(result, validation, imagens);
        File image = new File(FILE_NAME);
        controller.upload(new DefaultUploadedFile(FileUtils.openInputStream(image), image.getName(), "image/gif", image.getUsableSpace()));
        assertTrue(imagem.exists());
    }

    @Test
    public void removerArquivo() throws IOException {

        final Imagens imagens = new Imagens();
        final ImagemController controller = new ImagemController(result, validation, imagens);
        File image = new File(FILE_NAME);
        controller.upload(new DefaultUploadedFile(FileUtils.openInputStream(image), image.getName(), "image/gif", image.getUsableSpace()));
        assertTrue(imagem.exists());

        controller.remover("loading.gif");
        assertFalse(imagem.exists());
    }

}
