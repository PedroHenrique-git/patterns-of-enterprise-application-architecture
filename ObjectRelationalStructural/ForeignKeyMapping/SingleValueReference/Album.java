package ObjectRelationalStructural.ForeignKeyMapping.SingleValueReference;

public class Album {
    private String title;
    private Artist artist;

    public Album(long id, String title, Artist artist) {
        super(ID);

        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
