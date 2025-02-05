package DataSourceLayer.DataMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapper {
    protected Map loadedMap = new HashMap<>();

    abstract protected String findStatement();

    protected DomainObject abstractFind(long id) {
        DomainObject result = (DomainObject) loadedMap.get(id);

        if (result != null)
            return result;

        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare(findStatement());
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            rs.next();

            result = load(rs);

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    protected DomainObject load(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);

        if (loadedMap.containsKey(id))
            return (DomainObject) loadedMap.get(id);

        DomainObject result = doLoad(id, rs);

        loadedMap.put(id, result);
    }

    abstract DomainObject doLoad(long id, ResultSet rs) throws SQLException;

    protected List loadAll(ResultSet rs) throws SQLException {
        List result = new ArrayList<>();

        while (rs.next()) {
            result.add(load(rs));
        }

        return result;
    }

    public List findMany(StatementSource source) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(source.sql);

            for (int i = 0; i < source.parameters().length; i++)
                stmt.setObject(i + 1, source.parameters()[i]);

            rs = stmt.executeQuery();

            return loadAll(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    public long insert(DomainObject subject) {
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare(insertStatement());
            subject.setID(findNextDatabaseId());
            stmt.setInt(1, subject.getID());
            doInsert(subject, insertStatement());
            stmt.executeUpdate();
            loadedMap.put(subject.getID(), subject);
            return subject.getID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    abstract protected String insertStatement();

    abstract protected void doInsert(DomainObject subject, PreparedStatement stmt) throws SQLException;
}
