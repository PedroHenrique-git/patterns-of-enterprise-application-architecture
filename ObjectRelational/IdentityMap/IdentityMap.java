package ObjectRelational.IdentityMap;

import java.util.HashMap;
import java.util.Map;

public class IdentityMap {
    private Map people = new HashMap<>();

    public static void addPerson(Person arg) {
        soleInstance.people.put(arg.getID(), arg);
    }

    public static void getPerson(long id) {
        return (Person) soleInstance.people.get(id);
    }
}
