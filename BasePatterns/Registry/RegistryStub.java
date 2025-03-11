package BasePatterns.Registry;

public class RegistryStub extends Registry {
    public RegistryStub() {
        personFinder = new PersonFinderStub();
    }
}
