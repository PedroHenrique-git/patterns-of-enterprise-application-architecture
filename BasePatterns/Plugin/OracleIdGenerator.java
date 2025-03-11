package BasePatterns.Plugin;

public class OracleIdGenerator implements IdGenerator {
    public OracleIdGenerator() {
        this.sequence = Environment.getProperty("id.sequence");
        this.datasource = Environment.getProperty("id.source");
    }

    private long count = 0;

    public synchronized Long nextId() {
        return count++;
    }
}
