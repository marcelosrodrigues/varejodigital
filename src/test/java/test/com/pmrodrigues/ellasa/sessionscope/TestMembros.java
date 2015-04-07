package test.com.pmrodrigues.ellasa.sessionscope;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.sessionscope.Membros;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMembros {

    @Test
    public void remover() {

        final Membros membros = new Membros();

        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);

        Usuario usuario3 = new Usuario();
        usuario3.setId(3L);

        membros.adicionar(usuario1);
        membros.adicionar(usuario2);
        membros.adicionar(usuario3);

        membros.remover(usuario1);

        assertEquals(2, membros.getNovosMembros().size());
    }

    @Test
    public void removerUsuariosQueJaEstavamCadastrados() {
        final Membros membros = new Membros();

        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);

        Usuario usuario3 = new Usuario();
        usuario3.setId(3L);

        Usuario usuario4 = new Usuario();
        usuario4.setId(4L);

        membros.adicionar(usuario1);
        membros.adicionar(usuario2);
        membros.adicionar(usuario3);

        membros.remover(usuario4);

        assertEquals(3, membros.getNovosMembros().size());
        assertEquals(1, membros.getRemovidos().size());
    }

    @Test
    public void readicaoUsuarioQueEncontraSeNaListaDeExcluidos() {
        final Membros membros = new Membros();

        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);

        Usuario usuario3 = new Usuario();
        usuario3.setId(3L);

        Usuario usuario4 = new Usuario();
        usuario4.setId(4L);

        membros.adicionar(usuario1);
        membros.adicionar(usuario2);
        membros.adicionar(usuario3);

        membros.remover(usuario4);

        assertEquals(3, membros.getNovosMembros().size());
        assertEquals(1, membros.getRemovidos().size());

        membros.adicionar(usuario4);

        assertEquals(4, membros.getNovosMembros().size());
        assertEquals(0, membros.getRemovidos().size());
    }

}