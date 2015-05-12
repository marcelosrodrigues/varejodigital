package com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import com.pmrodrigues.varejodigital.annotations.Tiles;

/**
 * Created by Marceloo on 08/12/2014.
 */
@Resource
public class IndexController {

    @Tiles(definition = "index-template")
    @Path("/index.do")
    public void index() {
        //NOPMD
    }
}
