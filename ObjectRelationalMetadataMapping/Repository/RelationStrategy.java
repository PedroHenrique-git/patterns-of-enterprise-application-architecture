package ObjectRelationalMetadataMapping.Repository;

import java.util.List;

import javax.management.Query;

import ObjectRelationalMetadataMapping.QueryObject.Criteria;

public class RelationStrategy implements RepositoryStrategy {
    protected List matching(Criteria criteria) {
        Query query = new Query(myDomainObjectClass());
        query.addCriteria(criteria);

        return query.execute(unitOfWork());
    }
}
