package ObjectRelationalBehavioral.LazyLoad.Simple;

import java.util.List;

import DomainLayer.DomainModel.Product;

public class Supplier {
    public List getProducts() {
        if (products == null) {
            products = Product.findForSupplier(getID());
        }

        return products;
    }
}
