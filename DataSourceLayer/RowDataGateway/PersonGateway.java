package DataSourceLayer.RowDataGateway;

import java.rmi.registry.Registry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonGateway {
    private String lastName;
    private String firstName;
    private int numberOfDependents;

    private static final String UPDATE_STATEMENT = "UPDATE people SET lastname = ?, firstname = ?, number_of_dependents = ? WHERE id = ?";
    private static final String INSERT_STATEMENT = "INSERT INTO people VALUES(?,?,?,?)";

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    public void update() {
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare(UPDATE_STATEMENT);
            stmt.setString(1, lastName);
            stmt.setString(2, firstName);
            stmt.setInt(3, numberOfDependents);
            stmt.setInt(4, getID().intValue());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    public void insert() {
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare(INSERT_STATEMENT);
            stmt.setInt(1, getID().intValue());
            stmt.setString(2, lastName);
            stmt.setString(3, firstName);
            stmt.setInt(4, numberOfDependents);
            stmt.executeUpdate();

            Registry.addPerson(this);

            return getID();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    public static PersonGateway load(ResultSet rs) throws SQLException {
        Long id = Long.valueOf(rs.getLong(1));

        PersonGateway result = (PersonGateway) Registry.getPerson(id);

        if (result != null)
            return result;

        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numOfDependents = rs.getInt(4);

        result = new PersonGateway(id, lastNameArg, firstNameArg, numOfDependents);

        Registry.addPerson(result);

        return result;
    }
}
