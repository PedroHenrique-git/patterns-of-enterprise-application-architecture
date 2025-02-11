package ObjectRelational.LazyLoad.ValueHolder;

import java.util.List;

public class SupplierVH {
    private long id;
    private String name;
    private ValueHolder products;

    public SupplierVH(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public List getProducts() {
        return (List) products.getValue();
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

    public void setProducts(ValueHolder products) {
        this.products = products;
    }
}
