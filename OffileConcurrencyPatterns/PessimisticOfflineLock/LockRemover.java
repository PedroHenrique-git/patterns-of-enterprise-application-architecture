package OffileConcurrencyPatterns.PessimisticOfflineLock;

public class LockRemover implements HttpSessionBidingListener {
    private String sessionId;

    public LockRemover(String sessionId) {
        this.sessionId = sessionId;
    }

    public void valueUnbound(HttpSessionBidingEvent event) {
        try {
            beginSystemTransaction();
            ExclusiveReadLockManager.INSTANCE.releaseAllLocks(sessionId);
        } catch (Exception e) {
            handleSeriousError(e);
        }
    }
}
