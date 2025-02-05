package DataSourceLayer.DataMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonMapper extends AbstractMapper {
    public static final String COLUMNS = "id, lastname, firstname, number_of_dependents";

    protected String findStatement() {
        return "SELECT " + COLUMNS + " FROM people" + " WHERE id = ?";
    }

    public Person find(long id) {
        return (Person) abstractFind(id);
    }

    @Override
    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numOfDependents = rs.getInt(4);

        return new Person(id, lastNameArg, firstNameArg, numOfDependents);
    }

    private static final String FIND_BY_LAST_NAME = "SELECT " + COLUMNS + " FROM people"
            + " WHERE UPPER(lastname) like UPPER(?)" + " ORDER BY lastname";

    public List findByLastName(String name) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(FIND_BY_LAST_NAME);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    public List findByLastName2(String pattern) {
        return findMany(new FindByLastName(pattern));
    }

    static class FindByLastName implements StatementSource {
        private String lastName;

        public FindByLastName(String lastName) {
            this.lastName = lastName;
        }

        public String sql() {
            return "SELECT " + COLUMNS + " FROM people" + " WHERE UPPER(lastname) like UPPER(?)" + " ORDER BY lastname";
        }

        public Object[] parameters() {
            Object[] result = { lastName };

            return result;
        }
    }

    private static final String UPDATE_STATEMENT = "UPDATE people SET lastname = ?, firstname = ?, number_of_dependents = ? WHERE id = ?";

    public void update(Person subject) {
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare(UPDATE_STATEMENT);

            stmt.setString(1, subject.getLastName());
            stmt.setString(2, subject.getFirstName());
            stmt.setInt(3, subject.getNumberOfDependents());
            stmt.setInt(4, subject.getID());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    @Override
    protected String insertStatement() {
        return "INSERT INTO people VALUES(?, ?, ?, ?)";
    }

    protected void doInsert(DomainObject abstractSubject, PreparedStatement stmt) throws SQLException {
        Person subject = (Person) abstractSubject;

        stmt.setString(2, subject.getLastName());
        stmt.setString(3, subject.getFirstName());
        stmt.setInt(4, subject.getNumberOfDependents());
    }
}
