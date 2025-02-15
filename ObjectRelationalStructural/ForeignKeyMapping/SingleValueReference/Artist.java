package ObjectRelationalStructural.ForeignKeyMapping.SingleValueReference;

public class Artist {
    private String name;

    public Artist(long ID, String name) {
        super(ID);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
