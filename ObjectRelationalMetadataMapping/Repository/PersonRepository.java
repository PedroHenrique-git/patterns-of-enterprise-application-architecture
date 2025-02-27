package ObjectRelationalMetadataMapping.Repository;

import java.util.List;

import ObjectRelationalMetadataMapping.QueryObject.Criteria;

public class PersonRepository extends Repository {
    public List listDependents(Person aPerson) {
        Criteria criteria = new Criteria();

        criteria.equal(Person.BENEFACTOR, aPerson);

        return matching(criteria);
    }
}
