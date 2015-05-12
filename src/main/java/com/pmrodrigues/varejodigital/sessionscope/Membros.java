package com.pmrodrigues.varejodigital.sessionscope;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.pmrodrigues.varejodigital.models.Usuario;

/**
 * Created by Marceloo on 30/03/2015.
 */
@Component
@SessionScoped
public class Membros extends AbstractSimpleSessionScope<Usuario> {

}
