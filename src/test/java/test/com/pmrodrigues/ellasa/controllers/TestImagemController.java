package test.com.pmrodrigues.ellasa.controllers;

import br.com.caelum.vraptor.interceptor.multipart.DefaultUploadedFile;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import com.pmrodrigues.ellasa.controllers.ImagemController;
import com.pmrodrigues.ellasa.models.Imagem;
import com.pmrodrigues.ellasa.repositories.ImagemRepository;
import com.pmrodrigues.ellasa.sessionscope.Imagens;
import org.apache.commons.io.FileUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
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

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };


    @Test
    public void subirArquivo() throws IOException {
        final Imagens imagens = new Imagens();
        final ImagemController controller = new ImagemController(imagens, null);
        File image = new File(FILE_NAME);
        controller.upload(new DefaultUploadedFile(FileUtils.openInputStream(image), image.getName(), "image/gif", image.getUsableSpace()));
        assertTrue(imagem.exists());
    }

    @Test
    public void removerArquivo() throws IOException {

        final Imagens imagens = new Imagens();
        final ImagemController controller = new ImagemController(imagens, null);
        File image = new File(FILE_NAME);
        controller.upload(new DefaultUploadedFile(FileUtils.openInputStream(image), image.getName(), "image/gif", image.getUsableSpace()));
        assertTrue(imagem.exists());

        controller.remover("loading.gif");
        assertFalse(imagem.exists());
    }

    @Test
    public void removerArquivoJaSalvoNoBancoDeDados() {

        final ImagemRepository repository = context.mock(ImagemRepository.class);
        final Imagens imagens = new Imagens();

        context.checking(new Expectations() {{
            oneOf(repository).remove(with(aNonNull(Imagem.class)));
        }});

        final ImagemController controller = new ImagemController(imagens, repository);
        controller.remover(new Imagem());

    }

}
