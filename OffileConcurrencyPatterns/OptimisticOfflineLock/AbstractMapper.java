package OffileConcurrencyPatterns.OptimisticOfflineLock;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractMapper {
    private String table;
    private String[] columns;

    public AbstractMapper(String table, String[] columns) {
        this.columns = columns;
        this.table = table;
        buildStatements();
    }

    public DomainObject find(Long id) {
        DomainObject obj = AppSessionManager.getSession().getIdentityMap().get(id);

        if (obj == null) {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = ConnectionManager.INSTANCE.getConnection();
                stmt = conn.prepareStatement(loadSQL);
                stmt.setLong(1, id);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    obj = load(id, rs);
                    String modifiedBy = rs.getString(columns.length + 2);
                    Timestamp modified = rs.getTimestamp(columns.length + 3);
                    int version = rs.getInt(columns.length + 4);
                    obj.setSystemFields(modified, modifiedBy, version);
                    AppSessionManager.getSession().getIdentityMap().put(obj);
                } else {
                    throw new SystemException(table + "" + id + " does not exist");
                }
            } catch (SQLException e) {
                throw new SystemException(e);
            } finally {
                cleanupDBResources(rs, conn, stmt);
            }
        }

        return obj;
    }

    public void delete(DomainObject object) {
        AppSessionManager.getSession().getIdentityMap().remove(object.getId());
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionManager.INSTANCE.getConnection();
            stmt = conn.prepareStatement(deleteSQL);
            stmt.setLong(1, object.getId());
            int rowCount = stmt.executeUpdate();

            if (rowCount == 0) {
                throwConcurrencyException(object);
            }
        } catch (SQLException e) {
            throw new SystemException("unexpected error deleting");
        } finally {
            cleanupDBResources(conn, stmt);
        }
    }

    protected void throwConcurrencyException(DomainObject object) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.INSTANCE.getConnection();
            stmt = conn.prepareStatement(checkVersionSQL);
            stmt.setInt(1, (int) object.getId());
            rs = stmt.executeQuery();

            if (rs.next()) {
                int version = rs.getInt(1);
                String modifiedBy = rs.getString(2);
                Timestamp modified = rs.getTimestamp(3);

                if (version > object.getVersion()) {
                    String when = DataFormat.getDataTimeInstance().format(modified);
                    throw new ConcurrencyException(
                            table + "" + object.getId() + " modified by " + modifiedBy + " at" + when);
                } else {
                    throw new SystemException("unexpected error checking timestamp");
                }
            } else {
                throw new ConcurrencyException(table + "" + object.getId() + "has been deleted");
            }
        } finally {
            cleanupDBResources(rs, conn, stmt);
        }
    }

    protected String checkVersionSQL = "SELECT version, modifiedBy, modified FROM customer WHERE id = ?";

    protected abstract DomainObject load(Long id, ResultSet rs) throws SQLException;
}
