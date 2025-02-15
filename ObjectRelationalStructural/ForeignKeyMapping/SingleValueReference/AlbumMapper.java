package ObjectRelationalStructural.ForeignKeyMapping.SingleValueReference;

import java.sql.ResultSet;
import java.sql.SQLException;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public class AlbumMapper extends AbstractMapper {
    public Album find(long id) {
        return (Album) abstractFind(id);
    }

    protected String findStatement() {
        return "SELECT ID, title, artistID FROM albums WHERE id = ?";
    }

    @Override
    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        String title = rs.getString(2);
        long artistID = rs.getLong(3);

        Artist artist = MapperRegistry.artist().find(artistID);

        Album result = new Album(id, title, artist);

        return result;
    }
}
