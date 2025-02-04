package DataSourceLayer.DataMapper;

public class PersonMapper {
    public static final String COLUMNS = "id, lastname, firstname, number_of_dependents";

    protected String findStatement() {
        return "SELECT " + COLUMNS + " FROM people" + " WHERE id = ?";
    }

    public Person find(long id) {

    }
}
