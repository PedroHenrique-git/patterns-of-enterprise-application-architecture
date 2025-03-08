package OffileConcurrencyPatterns.CoarseGrainLock;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

import OffileConcurrencyPatterns.PessimisticOfflineLock.AppSessionManager;

public class Version {
    private long id;
    private long value;
    private String modifiedBy;
    private Timestamp modified;
    private boolean locked;
    private boolean isNew;

    private static final String UPDATE_SQL = "UPDATE version SET VALUE = ?, modifiedBy = ?, modified = ?"
            + " WHERE id = ? and value = ?";

    private static final String DELETE_SQL = "DELETE FROM version WHERE id = ? and value = ?";

    private static final String INSERT_SQL = "INSERT INTO version VALUES(?,?,?,?)";

    private static final String LOAD_SQL = "SELECT id, value, modifiedBy, modified FROM version WHERE id = ?";

    public static Version find(long id) {
        Version version = AppSessionManager.getSession().getIdentityMap().getVersion(id);

        if (version == null) {
            version = load(id);
        }

        return version;
    }

    public static Version load(long id) {
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        Version version = null;

        try {
            conn = ConnectionManager.INSTANCE.getConnection();
            stmt = conn.prepareStatement(LOAD_SQL);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                long value = rs.getLong(2);
                String modifiedBy = rs.getString(3);
                Timestamp modified = rs.getTimestamp(4);
                version = new Version(id, value, modifiedBy, modified);
                AppSessionManager.getSession().getIdentityMap().putVersion(version);
            } else {
                throw new ConcurrencyException("version " + id + " not found.");
            }
        } catch (SQLException sqlEx) {
            throw new SystemException("unexpected sql error loading version", sqlEx);
        } finally {
            cleanupDBResources(rs, conn, stmt);
        }

        return version;
    }

    public static Version create() {
        Version version = new Version(IdGenerator.INSTANCE.nextId(), 0, AppSessionManager.getSession().getUser(),
                now());
        version.isNew = true;
        return version;
    }

    public void insert() {
        if (isNew()) {
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = ConnectionManager.INSTANCE.getConnection();
                stmt = conn.prepareStatement(INSERT_SQL);
                stmt.setLong(1, getId());
                stmt.setLong(2, getValue());
                stmt.setString(3, getModifiedBy());
                stmt.setTimestamp(4, getModified());
                stmt.executeUpdate();
            } catch (SQLException sqlEx) {
                throw new SystemException("unexpected sql error inserting version", sqlEx);
            } finally {
                cleanupDBResources(conn, stmt);
            }
        }
    }

    public void increment() throws ConcurrencyException {
        if(!isLocked()) {
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = ConnectionManager.INSTANCE.getConnection();
                stmt = conn.prepareStatement(UPDATE_SQL);
                stmt.setLong(1, value + 1);
                stmt.setString(2, getModifiedBy());
                stmt.setTimestamp(3, getModified());
                stmt.setLong(4, id);
                stmt.setLong(5, value);
                int rowCount = stmt.executeUpdate();

                if(rowCount == 0) {
                    throwConcurrencyException();
                }
            }

            value++;
            locked = true;
        } catch(SQLException sqlEX) {
            throw new SystemException("unexpected sql error incrementing version", sqlEX);
        } finally {
            cleanupDBResources(conn, stmt);
        }
    }

    private void throwConcurrencyException() {
        Version currVersion = load(getId());

        throw new ConcurrencyException(
            "version modified by" + currentVersion.modifiedBy + " at " +
            DateFormat.getDateTimeInstance().format(currVersion.getModified());
        );
    }
}
