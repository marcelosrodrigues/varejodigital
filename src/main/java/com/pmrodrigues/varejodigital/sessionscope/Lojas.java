package com.pmrodrigues.varejodigital.sessionscope;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.pmrodrigues.varejodigital.models.Loja;

/**
 * Created by Marceloo on 13/04/2015.
 */
@SessionScoped
@Component
public class Lojas extends AbstractSimpleSessionScope<Loja> {
}
