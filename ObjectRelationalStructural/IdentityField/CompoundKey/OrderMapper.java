package ObjectRelationalStructural.IdentityField.CompoundKey;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper extends AbstractMapper {
    public Order find(Key key) {
        return (Order) abstractFind(key);
    }

    protected String findStatementString() {
        return "SELECT id, customer from orders WHERE id = ?";
    }

    @Override
    protected DomainObjectWithKey doLoad(Key id, ResultSet rs) throws SQLException {
        String customer = rs.getString("customer");
        Order result = new Order(key, customer);

        MapperRegistry.lineItem().loadAllLineItemsFor(result);

        return result;
    }
}
