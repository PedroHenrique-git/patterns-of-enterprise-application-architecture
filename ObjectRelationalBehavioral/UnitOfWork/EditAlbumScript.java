package ObjectRelationalBehavioral.UnitOfWork;

public class EditAlbumScript {
    public static void updateTitle(long albumId, String title) {
        UnitOfWork.newCurrent();

        Mapper mapper = MapperRegistry.getMapper(Album.class);

        Album album = (Album) mapper.find(albumId);

        album.setTitle(title);

        UnitOfWork.getCurrent().commit();
    }
}
