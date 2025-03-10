package BasePatterns.Gateway;

public interface Gateway {
    int send(String messageType, Object[] args);

    public void sendConfirmation(String orderID, int amount, String symbol);
}
