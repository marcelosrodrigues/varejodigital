package test.com.pmrodrigues.ellasa.tasks;

import com.pmrodrigues.ellasa.enumarations.StatusPagamento;
import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.pagamentos.v1.status.StatusPagamentoResponse;
import com.pmrodrigues.ellasa.repositories.PedidoRepository;
import com.pmrodrigues.ellasa.services.StatusPagamentoIntegrationService;
import com.pmrodrigues.ellasa.tasks.PedidoTask;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestPedidoTask {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private final StatusPagamentoIntegrationService service = context.mock(StatusPagamentoIntegrationService.class);
    private final PedidoRepository repository = context.mock(PedidoRepository.class);
    private final PedidoTask task = new PedidoTask();


    private void setField(String name, Object value) throws Exception {

        Field atributo = task.getClass().getDeclaredField(name);
        atributo.setAccessible(true);
        atributo.set(task, value);

    }

    @Before
    public void before() throws Exception {
        setField("repository", repository);
        setField("service", service);
    }


    @Test
    public void testUpdate() {

        final List<Pedido> pedidos = new ArrayList<>();
        final Pedido pedido = context.mock(Pedido.class);
        final StatusPagamentoResponse response = context.mock(StatusPagamentoResponse.class);

        pedidos.add(pedido);

        context.checking(new Expectations() {{
            oneOf(repository).listPedidosAguardandoPagamento();
            will(returnValue(pedidos));

            allowing(pedido).getCodigoTransacao();
            will(returnValue(""));

            oneOf(pedido).getCodigoReferencia();
            will(returnValue(""));

            oneOf(service).recuperarStatus(with(aNonNull(String.class)));
            will(returnValue(response));

            oneOf(response).getAtualizacao();
            will(returnValue(DateTime.now().toDate()));

            oneOf(pedido).setDataAprovacao(with(aNonNull(Date.class)));

            oneOf(response).getStatus();
            will(returnValue("Aprovado"));

            oneOf(pedido).setStatus(StatusPagamento.APROVADO);

            oneOf(repository).set(with(aNonNull(Pedido.class)));

        }});

        task.update();

    }

    @Test
    public void transacaoNaoEncontrada() {
        final List<Pedido> pedidos = new ArrayList<>();
        final Pedido pedido = context.mock(Pedido.class);
        final StatusPagamentoResponse response = context.mock(StatusPagamentoResponse.class);

        pedidos.add(pedido);

        context.checking(new Expectations() {{
            oneOf(repository).listPedidosAguardandoPagamento();
            will(returnValue(pedidos));

            allowing(pedido).getCodigoTransacao();
            will(returnValue(""));

            oneOf(pedido).getCodigoReferencia();
            will(returnValue(""));

            oneOf(service).recuperarStatus(with(aNonNull(String.class)));
            will(returnValue(response));

            oneOf(response).getAtualizacao();
            will(returnValue(DateTime.now().toDate()));

            oneOf(pedido).setDataAprovacao(with(aNonNull(Date.class)));

            oneOf(response).getStatus();
            will(returnValue("erro"));

            oneOf(response).getDescription();
            will(returnValue("transação não encontrada"));


            oneOf(pedido).setStatus(StatusPagamento.ERRO);
            oneOf(pedido).setMotivo(with(aNonNull(String.class)));

            oneOf(repository).set(with(aNonNull(Pedido.class)));

        }});

        task.update();
    }
}