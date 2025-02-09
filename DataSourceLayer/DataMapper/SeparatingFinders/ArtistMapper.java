package DataSourceLayer.DataMapper.SeparatingFinders;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistMapper extends AbstractMapper implements ArtistFinder {
    @Override
    public Artist find(long id) {
        return (Artist) abstractFind(id);
    }

    @Override
    protected String findStatement() {
        return "select " + COLUMN_LIST + " from artists art where ID = ?";
    }

    public static String COLUMN_LIST = "art.ID,art.name";

    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        Artist result = new Artist(id, name);

        return result;
    }
}
