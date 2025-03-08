package OffileConcurrencyPatterns.PessimisticOfflineLock;

import ObjectRelationalBehavioral.IdentityMap.IdentityMap;

public class AppSession {
    private String user;
    private String id;
    private IdentityMap imap;

    public AppSession(String user, String id, IdentityMap imap) {
        this.user = user;
        this.id = id;
        this.imap = imap;
    }
}
