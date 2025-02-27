package ObjectRelationalMetadataMapping.QueryObject;

public class Person {
    private String lastName;
    private String firstName;
    private int numberOfDependents;

    QueryObject query = new QueryObject(Person.class);
    // query.addCriteria(Criteria.greaterThan("numberOfDependents", 0));
}
