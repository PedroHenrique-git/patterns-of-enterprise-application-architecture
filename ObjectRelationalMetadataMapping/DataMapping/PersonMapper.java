package ObjectRelationalMetadataMapping.DataMapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public class PersonMapper extends Mapper {
    protected void loadDataMap() {
        dataMap = new DataMap(Person.class, "people");
        dataMap.addColumn("lastname", "varchar", "lastName");
        dataMap.addColumn("firstname", "varchar", "firstName");
        dataMap.addColumn("number_of_dependents", "int", "numberOfDependents");
    }

    public Person find(Long key) {
        return (Person) findObject(key);
    }

    public void update(DomainObject obj) {
        String sql = "UPDATE" + dataMap.getTableName() + dataMap.updateList() + "WHERE ID = ?";
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare(sql);

            int argCount = 1;

            for (Iterator it = dataMap.getColumns(); it.hasNext();) {
                ColumnMap col = (ColumnMap) it.next();
                stmt.setObject(argCount++, col.getValue(obj));
            }

            stmt.setLong(argCount, obj.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    public Set findLastNamesLike(String pattern) {
        String sql = "SELECT" + dataMap.columnList() + "FROM" + dataMap.getTableName()
                + "WHERE UPPER(lastName) like UPPER(?)";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(sql);
            stmt.setString(1, pattern);
            rs = stmt.executeQuery();
            return loadAll(rs);
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }
}
