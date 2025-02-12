package ObjectRelationalStructural.IdentityField.KeyTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyTable {
    private Connection conn;
    private String keyName;
    private long nextId;
    private long maxId;
    private int incrementBy;

    public KeyTable(Connection conn, String keyName, int incrementBy) {
        this.conn = conn;
        this.keyName = keyName;
        this.incrementBy = incrementBy;

        nextId = maxId = 0;

        try {
            conn.setAutoCommit(false);
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    public synchronized long nextKey() {
        if (nextId == maxId) {
            reserveIds();
        }

        return nextId++;
    }

    private void reserveIds() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        long newNextId;

        try {
            stmt = conn.prepareStatement("SELECT nextID FROM keys WHERE name ? FOR UPDATE");

            stmt.setString(1, keyName);
            rs = stmt.executeQuery();

            rs.next();
            newNextId = rs.getLong(1);
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        } finally {
            DB.cleanUp(stmt, rs);
        }

        long newMaxId = newNextId + incrementBy;
        stmt = null;

        try {
            stmt = conn.prepareStatement("UPDATE keys SET nextId = ? WHERE name = ?");
            stmt.setLong(1, newMaxId);
            stmt.setString(2, keyName);
            stmt.executeUpdate();

            conn.commit();

            nextId = newMaxId;
            maxId = newMaxId;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }
}
