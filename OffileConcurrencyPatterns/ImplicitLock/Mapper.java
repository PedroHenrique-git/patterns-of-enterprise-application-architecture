package OffileConcurrencyPatterns.ImplicitLock;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public interface Mapper {
    public DomainObject find(long id);

    public void insert(DomainObject obj);

    public void update(DomainObject obj);

    public void delete(DomainObject obj);
}
