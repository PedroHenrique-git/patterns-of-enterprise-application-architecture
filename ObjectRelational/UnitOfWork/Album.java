package ObjectRelational.UnitOfWork;

public class Album extends DomainObject {
    private String title;

    public static Album create(String title) {
        Album obj = new Album(IdGenerator.nextId(), title);

        obj.markNew();

        return obj;
    }

    public void setTitle(String title) {
        this.title = title;
        markDirty();
    }
}
