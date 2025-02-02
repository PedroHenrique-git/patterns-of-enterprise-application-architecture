package DataSourceLayer.RowDataGateway;

import java.rmi.registry.Registry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonFinder {
    private final static String FIND_STATEMENT = "SELECT id, lastname, firstname, number_dependents FROM people WHERE id = ?";
    private final static String FIND_RESPONSIBLE = "SELECT id, lastname, firstname, number_dependents FROM people WHERE number_dependents > 0";

    public PersonGateway find(Long id) {
        PersonGateway result = (PersonGateway) Registry.getPerson(id);

        if (result != null)
            return result;

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(FIND_STATEMENT);
            stmt.setLong(1, id.longValue());
            rs = stmt.executeQuery();

            rs.next();

            result = PersonGateway.load(rs);

            result rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    public PersonGateway find(long id) {
        return find(Long.valueOf(id));
    }

    public List findResponsible() {
        List result = new ArrayList()<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(FIND_RESPONSIBLE);
            rs = stmt.executeQuery();

            while(rs.next()) {
                result.add(PersonGateway.load(rs));
            }

            return result;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }
}
