package com.pmrodrigues.ellasa.sessionscope;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.pmrodrigues.ellasa.models.Loja;

/**
 * Created by Marceloo on 13/04/2015.
 */
@SessionScoped
@Component
public class Lojas extends AbstractSimpleSessionScope<Loja> {
}
