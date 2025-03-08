package OffileConcurrencyPatterns.CoarseGrainLock;

import java.security.Timestamp;

public class DomainObject {
    private long id;
    private DomainObject parent;
    private Timestamp modified;
    private String modifiedBy;
    private Version version;

    public DomainObject(long id, DomainObject parent, Timestamp modified, String modifiedBy, Version version) {
        this.id = id;
        this.parent = parent;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.version = version;
    }

    public void setSystemFields(Version version, Timestamp modified, String modifiedBy) {
        this.version = version;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }
}
