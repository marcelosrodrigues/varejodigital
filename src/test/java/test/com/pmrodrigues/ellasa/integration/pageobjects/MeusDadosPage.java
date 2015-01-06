package test.com.pmrodrigues.ellasa.integration.pageobjects;

import org.openqa.selenium.WebDriver;
import test.com.pmrodrigues.ellasa.integration.pageobjects.annotations.ById;
import test.com.pmrodrigues.ellasa.integration.pageobjects.annotations.URL;

/**
 * Created by Marceloo on 05/01/2015.
 */
@URL(url = "http://localhost:8080/meus-dados.do")
public class MeusDadosPage extends AbstractPageObject {

    @ById("object.nomeCompleto")
    private String nome;

    @ById("object.dataNascimento")
    private String dataNascimento;

    @ById("object.email")
    private String email;

    @ById("object.cpf")
    private String cpf;

    @ById("object.endereco.estado")
    private String estado;

    @ById("object.endereco.cidade")
    private String cidade;

    @ById("object.endereco.cep")
    private String cep;

    @ById("object.endereco.numero")
    private String numero;

    @ById("object.endereco.logradouro")
    private String logradouro;

    @ById("object.endereco.complemento")
    private String complemento;

    @ById("object.endereco.bairro")
    private String bairro;

    @ById("object.celular.ddd")
    private String dddCelular;

    @ById("object.celular.numero")
    private String numeroCelular;

    @ById("object.residencial.ddd")
    private String dddResidencial;

    @ById("object.residencial.numero")
    private String numeroResidencial;

    public MeusDadosPage(final WebDriver driver) {
        super(driver);
    }

    public MeusDadosPage nome(final String nomeCompleto) {
        this.nome = nomeCompleto;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public MeusDadosPage dataNascimento(final String dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public MeusDadosPage email(final String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public MeusDadosPage cpf(final String cpf) {
        this.cpf = cpf;
        return this;
    }

    public String getCPF() {
        return this.cpf;
    }

    public MeusDadosPage estado(final String estado) {
        this.estado = estado;
        return this;
    }

    public String getEstado() {
        return this.estado;
    }

    public MeusDadosPage cidade(final String cidade) {
        this.cidade = cidade;
        return this;
    }

    public String getCidade() {
        return cidade;
    }

    public MeusDadosPage cep(final String cep) {
        this.cep = cep;
        return this;
    }

    public String getCep() {
        return this.cep;
    }

    public MeusDadosPage numero(final String numero) {
        this.numero = numero;
        return this;
    }

    public String getNumero() {
        return this.numero;
    }

    public MeusDadosPage logradouro(final String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public MeusDadosPage complemento(final String complemento) {
        this.complemento = complemento;
        return this;
    }

    public String getComplemento() {
        return complemento;
    }

    public MeusDadosPage bairro(final String bairro) {
        this.bairro = bairro;
        return this;
    }

    public String getBairro() {
        return bairro;
    }

    public MeusDadosPage dddCelular(final String ddd) {
        this.dddCelular = ddd;
        return this;
    }

    public String getDddCelular() {
        return this.dddCelular;
    }

    public MeusDadosPage numeroCelular(final String numero) {
        this.numeroCelular = numero;
        return this;
    }

    public String getNumeroCelular() {
        return this.numeroCelular;
    }

    public MeusDadosPage dddResidencial(final String ddd) {
        this.dddResidencial = ddd;
        return this;
    }

    public String getDddResidencial() {
        return this.dddResidencial;
    }

    public MeusDadosPage numeroResidencial(final String numero) {
        this.numeroResidencial = numero;
        return this;
    }

    public String getNumeroResidencial() {
        return this.numeroResidencial;
    }

    @Override
    public AbstractPageObject submit() throws Exception {

        super.update(this);
        super.clickById("salvar");
        return super.to(UsuariosPage.class);

    }
}
