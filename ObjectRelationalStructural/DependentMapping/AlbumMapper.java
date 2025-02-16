package ObjectRelationalStructural.DependentMapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public class AlbumMapper {
    protected String findStatement() {
        return "SELECT ID, a.title, as trackTitle FROM albums a, tracks t WHERE a.ID = ? AND t.albumID = a.ID ORDER BY t.seq";
    }

    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        String title = rs.getString(2);

        Album result = new Album(id, title);

        loadTracks(result, rs);

        return result;
    }

    public void loadTracks(Album arg, ResultSet rs) throws SQLException {
        arg.addTrack(newTrack(rs));

        while (rs.next()) {
            arg.addTrack(newTrack(rs));
        }
    }

    private Track newTrack(ResultSet rs) throws SQLException {
        String title = rs.getString(3);

        Track newTrack = new Track(title);

        return newTrack;
    }

    public void update(DomainObject obj) {
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare("UPDATE albums SET title = ? WHERE id = ?");
            stmt.setLong(2, arg.getID());

            Album album = (Album) arg;

            stmt.setString(1, album.getTitle());
            stmt.executeUpdate();

            updateTracks(album);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt);
        }
    }

    public void updateTracks(Album arg) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare("DELETE from tracks WHERE albumID = ?");
            stmt.setLong(1, arg.getID());
            stmt.executeUpdate();

            for (int i = 0; i < arg.getTracks().length; i++) {
                Track track = arg.getTracks()[i];

                insertTrack(track, i + 1, arg);
            }
        } finally {
            DB.cleanUp(stmt);
        }
    }

    public void insertTrack(Track track, int seq, Album album) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = DB.prepare("INSERT INTO tracks(seq, albumID, title) VALUES(?,?,?)");
            stmt.setInt(1, seq);
            stmt.setLong(2, album.getID());
            stmt.setString(3, track.getTitle());
            stmt.executeUpdate();
        } finally {
            DB.cleanUp(stmt);
        }
    }
}
