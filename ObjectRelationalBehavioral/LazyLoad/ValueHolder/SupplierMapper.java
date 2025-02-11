package ObjectRelational.LazyLoad.ValueHolder;

import java.sql.ResultSet;
import java.sql.SQLException;

import ObjectRelational.UnitOfWork.DomainObject;

public class SupplierMapper {
    protected DomainObject doLoad(long id, ResultSet rs) throws SQLException {
        String nameArg = rs.getString(2);
        SupplierVH result = new SupplierVH(id, nameArg);

        result.setProducts(new ValueHolder(new ProductLoader(id)));

        return result;
    }

    public static class ProductLoader implements ValueLoader {
        private long id;

        public ProductLoader(long id) {
            this.id = id;
        }

        public Object load() {
            return ProductMapper.create().findForSupplier(id);
        }
    }
}
