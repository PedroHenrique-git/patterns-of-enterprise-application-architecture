package ObjectRelationalMetadataMapping.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;
import ObjectRelationalMetadataMapping.QueryObject.Criteria;

public class InMemoryStrategy {
    private Set domainObjects;

    protected List matching(Criteria criteria) {
        List results = new ArrayList<>();
        Iterator it = domainObjects.iterator();

        while (it.hasNext()) {
            DomainObject each = (DomainObject) it.next();

            if (criteria.isSatisfiedBy(each)) {
                results.add(each);
            }
        }

        return results;
    }
}
