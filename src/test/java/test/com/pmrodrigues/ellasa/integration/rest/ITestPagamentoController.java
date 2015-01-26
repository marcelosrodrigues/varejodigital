package test.com.pmrodrigues.ellasa.integration.rest;

import com.pmrodrigues.ellasa.models.Pedido;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import org.json.JSONException;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.String.format;

/**
 * Created by Marceloo on 16/01/2015.
 */
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class ITestPagamentoController extends AbstractTestRest<Pedido> {


    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private EstadoRepository estadoRepository;


    @After
    public void after() {
        super.after();

        jdbcTemplate.query("select id from cliente where email = 'marcelosrodrigues@globo.com'", new RowMapper<Void>() {
            @Override
            public Void mapRow(ResultSet rs, int rowNum) throws SQLException {


                Long cliente_id = rs.getLong("id");

                ITestPagamentoController.this.jdbcTemplate.query("select id from pedido where cliente_id = ?", new RowMapper<Void>() {

                    @Override
                    public Void mapRow(ResultSet resultset, int rowNum) throws SQLException {

                        ITestPagamentoController.this.jdbcTemplate.update("delete from comissao where item_id in (select id from item_pedido where pedido_id = ?)", resultset.getLong("id"));
                        ITestPagamentoController.this.jdbcTemplate.update("delete from item_pedido where pedido_id = ?", resultset.getLong("id"));
                        return null;
                    }
                }, cliente_id);
                ITestPagamentoController.this.jdbcTemplate.update("delete from pedido where cliente_id = ?", cliente_id);
                ITestPagamentoController.this.jdbcTemplate.update("delete from endereco where cliente_id = ?", cliente_id);
                ITestPagamentoController.this.jdbcTemplate.update("delete from cliente where id = ?", cliente_id);

                return null;
            }
        });
    }

    @Test
    public void efetuarPagamento() throws JSONException {

        final Long id = this.jdbcTemplate.queryForObject("select id from loja where nome = 'PROJETANDOO'", Long.class);
        final String toPay = "{ \"pedido\" : { \"itens\" :[ { \"item\": { \"produto\" : { \"id\": 132 },\"quantidade\": 1}}],\"pagamento\" : {\"numero\" : \"4012001038443335\",\"codigosegura\" : \"123\",\"dataExpiracao\" : \"2018-06-01  01:10:10.0 UTC\",\"portador\" : \"MARCELO DA SILVA RODRIGUES\",\"cpf\" : \"070.323.277-02\",\"telefone\" : \"2133926222\",\"meioPagamento\" : { \"id\" : 2 }},\"cliente\" : {\"primeiroNome\": \"MARCELO\" ,\"ultimoNome\": \"RODRIGUES\" ,\"email\": \"marcelosrodrigues@globo.com\" ,\"dataNascimento\": \"1977-08-17 01:10:10.0 UTC\" ,\"endereco\": {\"logradouro\": \"ESTRADA CAMPO DA AREIA, 84 APTO 206\",\"bairro\": \"PECHINCHA\" ,\"cep\": \"22743310\" ,\"cidade\": \"RIO DE JANEIRO\",\"telefone\": \"2133926222\" ,\"celular\": \"21981363699\" ,\"estado\": {\"id\": 330}}}}}";

        final Pedido pedido = this.post(format("http://localhost:8080/%s/carrinho-de-compras/checkout.json", id), "marsilvarodrigues@gmail.com", "12345678", toPay);
        Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido where loja_id = ?", Long.class, id);


    }

    @Test
    public void efetuarPagamentoSemAutenticacao() throws JSONException {
        final Long id = this.jdbcTemplate.queryForObject("select id from loja where nome = 'PROJETANDOO'", Long.class);
        final String toPay = "{ \"pedido\" : { \"itens\" :[ { \"item\": { \"produto\" : { \"id\": 132 },\"quantidade\": 1}}],\"pagamento\" : {\"numero\" : \"4012001038443335\",\"codigosegura\" : \"123\",\"dataExpiracao\" : \"2018-06-01  01:10:10.0 UTC\",\"portador\" : \"MARCELO DA SILVA RODRIGUES\",\"cpf\" : \"070.323.277-02\",\"telefone\" : \"2133926222\",\"meioPagamento\" : { \"id\" : 2 }},\"cliente\" : {\"primeiroNome\": \"MARCELO\" ,\"ultimoNome\": \"RODRIGUES\" ,\"email\": \"marcelosrodrigues@globo.com\" ,\"dataNascimento\": \"1977-08-17 01:10:10.0 UTC\" ,\"endereco\": {\"logradouro\": \"ESTRADA CAMPO DA AREIA, 84 APTO 206\",\"bairro\": \"PECHINCHA\" ,\"cep\": \"22743310\" ,\"cidade\": \"RIO DE JANEIRO\",\"telefone\": \"2133926222\" ,\"celular\": \"21981363699\" ,\"estado\": {\"id\": 330}}}}}";

        final Pedido pedido = this.post(format("http://localhost:8080/%s/carrinho-de-compras/checkout.json", id), toPay);
        Long count = this.jdbcTemplate.queryForObject("select count(1) from pedido where loja_id = ?", Long.class, id);
    }


}
