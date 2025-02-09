package DataSourceLayer.DataMapper.EmptyObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractMapper {
    protected DomainObjectEL load(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");

        if (loadedMap.containsKeys(id)) {
            return (DomainObjectEL) loadedMap.get(id);
        }

        DomainObjectEL result = createDomainObject();

        result.setID(id);
        loadedMap.put(id, result);
        doLoad(result, rs);
        return result;
    }

    abstract protected DomainObjectEL createDomainObject();

    abstract protected void doLoad(DomainObjectEL obj, ResultSet rs) throws SQLException;
}
