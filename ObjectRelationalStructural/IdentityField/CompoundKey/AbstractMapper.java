package ObjectRelationalStructural.IdentityField.CompoundKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapper {
    abstract protected String findStatementString();

    protected Map loadedMap = new HashMap<>();

    public DomainObjectWithKey abstractFind(Key key) {
        DomainObjectWithKey result = (DomainObjectWithKey) loadedMap.get(key);

        if (result != null) {
            return result;
        }

        ResultSet rs = null;
        PreparedStatement findStatement = null;

        try {
            findStatement = DB.prepare(findStatementString());
            loadFindStatement(key, findStatement);

            rs = findStatement.executeQuery();

            rs.next();

            if (rs.isAfterLast())
                return null;

            result = load(rs);

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(findStatement, rs);
        }
    }

    protected void loadFindStatement(Key key, PreparedStatement stmt) throws SQLException {
        stmt.setLong(1, key.longValue());
    }

    protected DomainObjectWithKey load(ResultSet rs) throws SQLException {
        Key key = createKey(rs);

        if (loadedMap.containsKey(key))
            return (DomainObjectWithKey) loadedMap.get(key);

        DomainObjectWithKey result = doLoad(key, rs);

        loadedMap.put(key, result);

        return result;
    }

    abstract protected DomainObjectWithKey doLoad(Key id, ResultSet rs) throws SQLException;

    protected Key createKey(ResultSet rs) throws SQLException {
        return new Key(rs.getLong(1));
    }
}
