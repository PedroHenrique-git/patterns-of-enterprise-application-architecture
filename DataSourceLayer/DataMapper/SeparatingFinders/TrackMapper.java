package DataSourceLayer.DataMapper.SeparatingFinders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Track;

public class TrackMapper extends AbstractMapper implements TrackFinder {
    public static final String FIND_FOR_ALBUM_STATEMENT = "SELECT ID, seq, albumID, title" +
            " FROM tracks" +
            " WHERE albumID = ? ORDER BY seq";

    @Override
    public Track findForAlbum(long albumID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(FIND_FOR_ALBUM_STATEMENT);
            stmt.setLong(1, albumID);

            rs = stmt.executeQuery();

            List result = new ArrayList<>();

            while (rs.next()) {
                result.add(load(rs));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(stmt, rs);
        }
    }
}
