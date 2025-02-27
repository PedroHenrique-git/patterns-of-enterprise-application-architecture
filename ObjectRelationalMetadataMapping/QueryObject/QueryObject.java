package ObjectRelationalMetadataMapping.QueryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ObjectRelationalBehavioral.UnitOfWork.UnitOfWork;

public class QueryObject {
    private Class klass;
    private UnitOfWork uow;
    private List criteria = new ArrayList<>();

    public QueryObject(Class klass) {
        this.klass = klass;
    }

    public Set execute(UnitOfWork uow) {
        this.uow = uow;

        return uow.getMapper(klass).findObjectsWhere(generateWhereClause());
    }
}
