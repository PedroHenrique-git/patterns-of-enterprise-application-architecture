package ObjectRelationalBehavioral.LazyLoad.VirtualProxy;

import java.util.List;

public class SupplierVL {
    private long id;
    private String name;
    private List products;

    public SupplierVL(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(VirtualList virtualList) {
        this.products = virtualList.getLoader().load();
    }
}
