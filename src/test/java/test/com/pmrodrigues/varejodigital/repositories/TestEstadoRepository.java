package test.com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Estado;
import com.pmrodrigues.varejodigital.repositories.EstadoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class TestEstadoRepository
        extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private EstadoRepository repository;

    @Test
    public void testFindById() {
        final Estado estado = repository.findById(313L);
        Assert.assertNotNull(estado);
    }

    @Test
    public void testList() {
        final List<Estado> estados = repository.list();
        Assert.assertFalse(estados.isEmpty());
    }

}
