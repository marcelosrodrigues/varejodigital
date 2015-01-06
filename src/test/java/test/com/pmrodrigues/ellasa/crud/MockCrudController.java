package test.com.pmrodrigues.ellasa.crud;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.pmrodrigues.ellasa.annotations.After;
import com.pmrodrigues.ellasa.annotations.Before;
import com.pmrodrigues.ellasa.annotations.Insert;
import com.pmrodrigues.ellasa.annotations.Update;
import com.pmrodrigues.ellasa.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.repositories.Repository;

/**
 * Created by Marceloo on 02/01/2015.
 */
public class MockCrudController extends AbstractCRUDController<Loja> {

    protected MockCrudController(Repository<Loja> repository, Result result, Validator validator) {
        super(repository, result, validator);
    }

    @After
    public void postExecute(Loja loja) {
        super.getResult().include("teste","teste");
    }

    @Before
    public void preExecute() {
        super.getResult().include("teste","teste");
    }

    @Insert
    public void doInsert(Loja e){
        super.getResult().include("teste","teste");
    }

    @Update
    public void doUpdate(Loja e){
        super.getResult().include("teste","teste");
    }
}
