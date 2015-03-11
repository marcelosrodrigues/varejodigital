package test.com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.pagamentos.v1.status.StatusPagamentoResponse;
import com.pmrodrigues.ellasa.services.StatusPagamentoIntegrationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestStatusPagamentoIntegrationService extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private StatusPagamentoIntegrationService service;

    @Test
    public void testRecuperarStatus() throws Exception {

        StatusPagamentoResponse response = service.recuperarStatus("126975C0-982E-477F-878E-D973611BD625");
        assertNotNull(response);

    }
}