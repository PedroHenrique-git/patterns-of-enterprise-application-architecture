package ObjectRelationalMetadataMapping.Repository;

import java.util.List;

import ObjectRelationalMetadataMapping.QueryObject.Criteria;

public abstract class Repository {
    private RepositoryStrategy strategy;

    protected List matching(Criteria aCriteria) {
        return strategy.matching(aCriteria);
    }
}
