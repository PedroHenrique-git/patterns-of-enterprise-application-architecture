package ObjectRelationalStructural.IdentityField.CompoundKey;

import java.sql.PreparedStatement;
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

    @Override
    protected String insertStatementString() {
        return "INSERT INTO order VALUES(?,?)";
    }

    @Override
    protected void insertData(DomainObjectWithKey subject, PreparedStatement stmt) throws SQLException {
        try {
            Order subject = (Order) abstractSubject;
            stmt.setString(2, subject.getCustomer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void loadUpdateStatement(DomainObjectWithKey subject, PreparedStatement stmt) throws SQLException {
        Order order = (Order) subject;

        stmt.setString(1, order.getCustomer());
        stmt.setString(2, order.getKey().longValue());
    }

    @Override
    protected String updateStatementString() {
        return "UPDATE orders SET customer ? WHERE id = ?";
    }

    @Override
    protected String deleteStatementString() {
        return "DELETE FROM orders WHERE id = ?";
    }
}
