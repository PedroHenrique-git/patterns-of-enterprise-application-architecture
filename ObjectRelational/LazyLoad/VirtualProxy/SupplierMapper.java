package ObjectRelational.LazyLoad.VirtualProxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ObjectRelational.UnitOfWork.DomainObject;

public class SupplierMapper {
    public static class ProductLoader implements VirtualListLoader {
        private long id;

        public ProductLoader(long id) {
            this.id = id;
        }

        public List load() {
            return ProductMapper.create().findForSupplier(id);
        }
    }

    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        String nameArg = rs.getString(2);
        SupplierVL result = new SupplierVL(id, nameArg);

        result.setProducts(new VirtualList(new ProductLoader(id)));

        return result;
    }
}
