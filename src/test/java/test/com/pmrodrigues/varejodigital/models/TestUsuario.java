package test.com.pmrodrigues.varejodigital.models;

import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.models.Usuario;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUsuario {

    @Test
    public void testIncrementarTentativasFalhas() throws Exception {

        final Usuario usuario = new Usuario();
        final Field tentativas = usuario.getClass().getDeclaredField("tentativas");
        tentativas.setAccessible(true);

        usuario.incrementarTentativasFalhas();
        Long value = (Long) tentativas.get(usuario);
        assertEquals(Long.valueOf(1L), value);

    }

    @Test
    public void bloquearAposNumeroMaximoTentativasInvalidades() throws Exception {

        final Usuario usuario = new Usuario();
        final Field tentativas = usuario.getClass().getDeclaredField("tentativas");
        tentativas.setAccessible(true);

        for (Long i = 0L; i < Constante.NUMERO_MAXIMO_TENTATIVAS_FALHAS; i++) {
            usuario.incrementarTentativasFalhas();
        }

        assertTrue(usuario.isBloqueado());
        Long value = (Long) tentativas.get(usuario);
        assertEquals(Long.valueOf(5L), value);

    }
}