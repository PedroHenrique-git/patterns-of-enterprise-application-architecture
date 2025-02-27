package ObjectRelationalMetadataMapping.Repository;

import java.rmi.registry.Registry;
import java.util.List;

import ObjectRelationalMetadataMapping.QueryObject.Criteria;

public class Person {
    public List dependents() {
        Repository repository = Registry.personRepository();
        Criteria criteria = new Criteria();

        criteria.equal(Person.BENEFACTOR, this);

        return repository.matching(criteria);
    }

    public List dependents() {
        return Registry.personRepository().dependentsOf(this);
    }
}
