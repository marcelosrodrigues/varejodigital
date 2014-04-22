package test.com.pmrodrigues.ellasa.services;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import test.com.pmrodrigues.ellasa.Factory;

import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.models.FranqueadoPessoaFisica;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.models.OrdemPagamentoCartaoCredito;
import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;
import com.pmrodrigues.ellasa.services.ContratoService;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestContratoService
		extends
			AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ContratoService service;

	@Autowired
	private TipoFranquiaRepository franquiaRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private MeioPagamentoRepository meioPagamentoRepository;

	@Test
	public void assinar() {

		final TipoFranquia franquia = franquiaRepository.findById(4L);
		final MeioPagamento meiopagamento = meioPagamentoRepository
				.findById(2L);
		final Estado estado = estadoRepository.findById(313L);
		final FranqueadoPessoaFisica franqueado = Factory.getStubFranqueado(estado);

		final OrdemPagamentoCartaoCredito ordem = new OrdemPagamentoCartaoCredito();
		final Contrato contrato = new Contrato();
		contrato.setFranqueado(franqueado);
		contrato.setTipoFranquia(franquia);
		ordem.setMeioPagamento(meiopagamento);
		ordem.setValor(franquia.getValor());
		ordem.setDescricao("teste");

		ordem.setNumero("4012001038443335");
		ordem.setCodigoSeguranca("123");
		ordem.setPortador("MARCELO DA S RODRIGUES");
		DateTime dataexpiracao = new DateTime(2018, 5, 01, 0, 0);
		ordem.setDataExpiracao(dataexpiracao.toDate());
		ordem.setCPF("070.323.277-02");
		ordem.setTelefone("02133926222");
		ordem.setContrato(contrato);

		service.assinar(ordem);

	}
}
