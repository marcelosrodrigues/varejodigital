package test.com.pmrodrigues.varejodigital.crud;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.pmrodrigues.varejodigital.annotations.After;
import com.pmrodrigues.varejodigital.annotations.Before;
import com.pmrodrigues.varejodigital.annotations.Insert;
import com.pmrodrigues.varejodigital.annotations.Update;
import com.pmrodrigues.varejodigital.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.repositories.Repository;

/**
 * Created by Marceloo on 02/01/2015.
 */
public class MockCrudController extends AbstractCRUDController<Loja> {

    protected MockCrudController(Repository<Loja> repository, Result result, Validator validator) {
        super(repository, result, validator);
    }

    @After
    public void postExecute(Loja loja) {
        super.getResult().include("teste", "teste");
    }

    @Before
    public void preExecute() {
        super.getResult().include("teste", "teste");
    }

    @Insert
    public void doInsert(Loja e) {
        super.getResult().include("teste", "teste");
    }

    @Update
    public void doUpdate(Loja e) {
        super.getResult().include("teste", "teste");
    }
}
