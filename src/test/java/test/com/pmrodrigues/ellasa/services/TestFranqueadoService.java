package test.com.pmrodrigues.ellasa.services;

import java.lang.reflect.Field;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import com.pmrodrigues.ellasa.exceptions.IndicacaoFranqueadoNaoEncontradoException;
import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;
import com.pmrodrigues.ellasa.services.EmailService;
import com.pmrodrigues.ellasa.services.FranqueadoService;

public class TestFranqueadoService {

	private final Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private FranqueadoRepository repository;
	private EmailService email;
	private final FranqueadoService service = new FranqueadoService();

	@Before
	public void before() throws Exception {
		repository = context.mock(FranqueadoRepository.class);
		email = context.mock(EmailService.class);

		final Field repository = service.getClass().getDeclaredField(
				"repository");
		repository.setAccessible(true);
		repository.set(service, this.repository);

		final Field email = service.getClass().getDeclaredField("email");
		email.setAccessible(true);
		email.set(service, this.email);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void salvarFranqueado() throws Exception {

		final Franqueado franqueado = new Franqueado();
		franqueado.setEmail("");
		
		context.checking(new Expectations() {
			{
				oneOf(repository).findByCodigo(with(aNonNull(String.class)));
				will(returnValue(franqueado));

				oneOf(repository).add(with(aNonNull(Franqueado.class)));

				oneOf(email).from(with(aNonNull(String.class)));
				will(returnValue(email));

				oneOf(email).to(with(aNonNull(String.class)));
				will(returnValue(email));

				oneOf(email).cc(with(aNonNull(String.class)));
				will(returnValue(email));

				oneOf(email).subject(with(aNonNull(String.class)));
				will(returnValue(email));

				oneOf(email).template(with(aNonNull(String.class)),
						with(aNonNull(Map.class)));
				will(returnValue(email));

				oneOf(email).send();
			}
		});

		Franqueado newbie = new Franqueado();
		newbie.setEmail("");
		service.adicionar(newbie, "REFERENCIA");
	}

	@Test(expected = IndicacaoFranqueadoNaoEncontradoException.class)
	public void naoPodeSalvarFranqueadoSemReferencia() throws Exception {

		context.checking(new Expectations() {
			{
				oneOf(repository).findByCodigo(with(aNonNull(String.class)));
				will(returnValue(null));

			}
		});

		service.adicionar(new Franqueado(), "REFERENCIA");

		
	}

}
