package ObjectRelationalMetadataMapping.DataMapping;

public class PersonMapper {
    protected void loadDataMap() {
        dataMap = new DataMap(Person.class, "people");
        dataMap.addColumn("lastname", "varchar", "lastName");
        dataMap.addColumn("firstname", "varchar", "firstName");
        dataMap.addColumn("number_of_dependents", "int", "numberOfDependents");
    }
}
