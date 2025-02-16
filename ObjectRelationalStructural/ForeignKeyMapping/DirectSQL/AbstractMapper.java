package ObjectRelationalStructural.ForeignKeyMapping.DirectSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public abstract class AbstractMapper {
    protected DomainObject abstractFind(long id) {
        DomainObject result = (DomainObject) loadedMap.get(id);

        if (result != null)
            return result;

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(findStatement());
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            rs.next();

            result = load(rs);

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    abstract protected String findStatement();

    protected Map loadedMap = new HashMap<>();

    protected DomainObject load(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);

        return load(id, rs);
    }

    public DomainObject load(long id, ResultSet rs) throws SQLException {
        if (hasLoaded(id))
            return (DomainObject) loadedMap.get(id);

        DomainObject result = doLoad(id, rs);
        loadedMap.put(id, result);

        return result;
    }

    abstract protected DomainObject doLoad(long id, ResultSet rs) throws SQLException;

    protected DomainObject loadRow(long id, ResultSet rs) throws SQLException {
        return load(id, rs);
    }

    protected List findAll(String sql) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            List<E> result = new ArrayList<>();

            stmt = DB.prepare(sql);
            rs = stmt.executeQuery();

            while (rs.next())
                result.add(load(rs));

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }
}
