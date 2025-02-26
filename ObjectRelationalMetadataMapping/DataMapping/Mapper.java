package ObjectRelationalMetadataMapping.DataMapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;
import ObjectRelationalBehavioral.UnitOfWork.UnitOfWork;

public class Mapper {
    public Object findObject(Long key) {
        if (uow.isLoaded(key)) {
            return uow.getObject(key);
        }

        String sql = "SELECT" + dataMap.columnsList() + "FROM" + dataMap.getTableName() + "WHERE ID = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        DomainObject result = null;

        try {
            stmt = DB.prepare(sql);
            stmt.setLong(1, key);
            rs = stmt.executeQuery();

            rs.next();

            result = load(rs);
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }

        return result;
    }

    public DomainObject load(ResultSet rs) throws InstantiationException, IllegalAccessError, SQLException {
        Long key = rs.getLong("ID");

        if (uow.isLoaded(key)) {
            return uow.getObject(key);
        }

        DomainObject result = (DomainObject) dataMap.getDomainClass().newInstance();

        result.setID(key);
        uow.registerClean(result);
        loadFields(rs, result);
        return result;
    }

    private void loadFields(ResultSet rs, DomainObject obj) throws SQLException {
        for (Iterator it = dataMap.getColumns(); it.hasNext();) {
            ColumnMap columnMap = (ColumnMap) it.next();
            Object columnValue = rs.getObject(columnMap.getColumnName());
            columnMap.setField(result, columnValue);
        }
    }

    private UnitOfWork uow;
    protected DataMap dataMap;
}
