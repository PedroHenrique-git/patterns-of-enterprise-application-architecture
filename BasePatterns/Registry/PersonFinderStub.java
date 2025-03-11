package BasePatterns.Registry;

public class PersonFinderStub {
    public Person find(long id) {
        if (id == 1) {
            return new Person("Fowler", "Martin", 10);
        }

        throw new IllegalArgumentException("Can't find id: " + String.valueOf(id));
    }
}
