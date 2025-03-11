package BasePatterns.Registry;

import DataSourceLayer.DataMapper.Person;
import DataSourceLayer.RowDataGateway.PersonFinder;

public class ThreadLocalRegistry {
    private static ThreadLocal instances = new ThreadLocal<>();

    public static ThreadLocalRegistry getInstance() {
        return (ThreadLocalRegistry) instances.get();
    }

    public static void begin() {
        Asset.isTrue(instances.get() == null);
        instances.set(new ThreadLocalRegistry());
    }

    public static void end() {
        Asset.notNull(getInstance());
        instances.set(null);
    }

    private PersonFinder personFinder = new PersonFinder();

    public static PersonFinder personFinder() {
        return getInstance().personFinder;
    }

    ...
    try {
        ThreadLocalRegistry.begin();

        PersonFinder f1 = ThreadLocalRegistry.personFinder();
        Person martin = Registry.personFinder().find(1);
        assertEquals("Fowler", martin.getLastName());
    } finally {
        ThreadLocalRegistry.end();
    }
    ...
}
