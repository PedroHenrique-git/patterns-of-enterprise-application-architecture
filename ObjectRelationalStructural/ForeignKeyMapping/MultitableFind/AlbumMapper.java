package ObjectRelationalStructural.ForeignKeyMapping.MultitableFind;

import java.sql.ResultSet;
import java.sql.SQLException;

import ObjectRelationalBehavioral.UnitOfWork.DomainObject;

public class AlbumMapper extends AbstractMapper {
    public Album find(long id) {
        return (Album) abstractFind(id);
    }

    protected String findStatement() {
        return "SELECT a.ID, a.title, a.artistID, r.name FROM albums a, artist r WHERE ID = ? and a.artistID = r.ID";
    }

    @Override
    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        String title = rs.getString(2);
        long artistID = rs.getLong(3);

        Artist artist = MapperRegistry.artist();
        Artist artist;

        if (artistMapper.isLoaded(artistID))
            artist = artistMapper.find(artistID);
        else
            artist = loadArtist(artistID, rs);

        Album result = new Album(id, title, artist);

        return result;
    }

    private Artist loadArtist(long id, ResultSet rs) throws SQLException {
        String name = rs.getString(4);
        Artist result = new Artist(id, name);

        MapperRegistry.artist().register(result.getID(), result);

        return result;
    }
}
