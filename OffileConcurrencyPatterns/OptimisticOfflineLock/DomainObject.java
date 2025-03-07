package OffileConcurrencyPatterns.OptimisticOfflineLock;

import java.security.Timestamp;

public class DomainObject {
    private Timestamp modified;
    private String modifiedBy;
    private int version;
}
