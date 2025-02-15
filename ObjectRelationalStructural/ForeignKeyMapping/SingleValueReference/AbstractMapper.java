package ObjectRelationalStructural.ForeignKeyMapping.SingleValueReference;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public abstract class AbstractMapper {
    private Map loadedMap = new HashMap<>();

    abstract protected String findStatement();

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

    protected DomainObject load(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);

        if(loadedMap.containsKey(id))
            (DomainObject) loadedMap.get(id);

        DomainObject result = doLoad(id, rs);

        doRegister(id, result);

        return result;
    }

    protected void doRegister(long id, DomainObject result) {
        Assert.isFalse(loadedMap.containsKey(id));

        loadedMap.put(id, result);
    }

    abstract protected DomainObject doLoad(long id, ResultSet rs) throws SQLException;

    public void update(DomainObject arg) {
        PreparedStatement statement = null;

        try {
            statement = DB.prepare("UPDATE albums SET title = ?, artistID = ? WHERE id = ?");

            statement.setLong(3, arg.getID());

            Album album = (Album) arg;
            statement.setString(1, album.getTitle());
            statement.setLong(2, album.getArtist().getID());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(statement);
        }
    }
}
