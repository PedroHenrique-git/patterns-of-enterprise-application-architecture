package OffileConcurrencyPatterns.PessimisticOfflineLock;

public class TransactionalCommand implements Command {
    private Command impl;

    public TransactionalCommand(Command impl) {
        this.impl = impl;
    }

    @Override
    public void process() throws Exception {
        beginSystemTransaction();

        try {
            impl.process();
            commitSystemTransaction();
        } catch (Exception e) {
            rollbackSystemTransaction();

            throw e;
        }
    }
}
