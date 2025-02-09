package DataSourceLayer.DataMapper.EmptyObject;

public class DomainObjectEL {
    private int state = LOADING;
    private static final int LOADING = 0;
    private static final int ACTIVE = 1;

    public void beActive() {
        state = ACTIVE;
    }

    public void assertStateIsLoading() {
        Assert.isTrue(state == LOADING);
    }
}
