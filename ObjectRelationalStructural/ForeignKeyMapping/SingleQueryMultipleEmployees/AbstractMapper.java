package ObjectRelationalStructural.ForeignKeyMapping.SingleQueryMultipleEmployees;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public class AbstractMapper {
    boolean hasLoaded(Long id) {
        return loadedMap.containsKey(id);
    }

    void putAsLoaded(DomainObject obj) {
        loadedMap.put(obj.getID(), obj);
    }

    protected DomainObject lookUp(Long id) {
        return (DomainObject) loadedMap.get(id);
    }
}
