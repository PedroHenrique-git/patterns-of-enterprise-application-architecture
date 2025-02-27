package ObjectRelationalMetadataMapping.QueryObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Mapper {
    public Set findObjectsWhere(String whereClause) {
        String sql = "SELECT" + dataMap.columnsList() + "FROM" + dataMap.getTableName() + "WHERE" + whereClause;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Set result = new HashSet<>();

        try {
            stmt = DB.prepare(sql);
            rs = stmt.executeQuery();
            result = loadAll(rs);
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }

        return result;
    }

    private String generateWhereClause() {
        StringBuffer result = new StringBuffer();

        for (Iterator it = criteria.iterator(); it.hasNext();) {
            Criteria c = (Criteria) it.next();

            if (result.length() != 0) {
                result.append(" AND ");
            }

            result.append(c.generateSql(uow.getMapper(klass).getDataMap()));
        }

        return result.toString();
    }
}
