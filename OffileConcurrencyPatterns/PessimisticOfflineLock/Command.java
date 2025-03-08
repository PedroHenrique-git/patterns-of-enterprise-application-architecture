package OffileConcurrencyPatterns.PessimisticOfflineLock;

public interface Command {
    public void init(HttpServletRequest req, HttpServletResponse resp);

    public void process() throws Exception;
}
