package test.com.pmrodrigues.varejodigital.sessionscope;

import com.pmrodrigues.varejodigital.sessionscope.Atributos;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAtributos {

    @Test
    public void testRemover() throws Exception {

        Atributos atributos = new Atributos();
        atributos.adicionar("A");
        atributos.adicionar("A");
        atributos.adicionar("B");
        atributos.adicionar("C");

        assertEquals(3, atributos.getAtributos().size());

        atributos.remover("A");

        assertEquals(2, atributos.getAtributos().size());

    }
}
