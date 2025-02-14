package ObjectRelationalStructural.IdentityField.CompoundKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class LineItemMapper extends AbstractMapper {
    public LineItem find(long orderID, long seq) {
        Key key = new Key(orderID, seq);

        return (LineItem) abstractFind(key);
    }

    public LineItem find(Key key) {
        return (LineItem) abstractFind(key);
    }

    protected String findStatementString() {
        return "SELECT orderID, seq, amount, product FROM line_items WHERE (orderID = ?) AND (sql = ?)";
    }

    protected void loadFindStatement(Key key, PreparedStatement stmt) {
        stmt.setLong(1, orderID(key));
        stmt.setLong(2, sequenceNumber(key));
    }

    private static long orderID(Key key) {
        return key.longValue();
    }

    private static long sequenceNumber(Key key) {
        return key.longValue(1);
    }

    @Override
    protected DomainObjectWithKey doLoad(Key id, ResultSet rs) throws SQLException {
        Order theOrder = MapperRegistry.order().find(orderID(id));

        return doLoad(key, rs, theOrder);
    }

    protected DomainObjectWithKey doLoad(Key key, ResultSet rs, Order order) throws SQLException {
        LineItem result;
        int amount = rs.getInt("amount");
        String product = rs.getString("product");

        result = new LineItem(key, amount, product);

        order.addLineItem(result);

        return result;
    }

    protected Key createKey(ResultSet rs) throws SQLException {
        Key key = new Key(rs.getLong("orderID"), rs.getLong("seq"));
        return key;
    }

    public void loadAllLinesItemsFor(Order arg) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = DB.prepare(findForOrderString);
            stmt.setLong(1, arg.getKey().longValue());
            rs = stmt.executeQuery();

            while (rs.next()) {
                load(rs, arg);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(stmt, rs);
        }
    }

    private final static String findForOrderString = "SELECT orderID, seq, amount, product FROM line_items WHERE orderID = ?";

    protected DomainObjectWithKey load(ResultSet rs, Order order) throws SQLException {
        Key key = createKey(rs);

        if (loadedMap.containsKey(key)) {
            return (DomainObjectWithKey) loadedMap.get(key);
        }

        DomainObjectWithKey result = doLoad(key, rs, order);
        loadedMap.put(key, result);

        return result;
    }

    @Override
    protected String insertStatementString() {
        return "INSERT INTO line_items VALUES(?,?,?,?)";
    }

    protected void insertKey(DomainObjectWithKey subject, PreparedStatement stmt) {
        stmt.setLong(1, orderID(subject.getKey()));
        stmt.setLong(2, sequenceNumber(subject.getKey()));
    }

    @Override
    protected void insertData(DomainObjectWithKey subject, PreparedStatement stmt) throws SQLException {
        LineItem item = (LineItem) subject;

        stmt.setInt(3, item.getAmount());
        stmt.setString(4, item.getProduct());
    }

    @Override
    public Key insert(DomainObjectWithKey subject) {
        throw new UnsupportedOperationException("Must supply an order when inserting a line item");
    }

    public Key insert(LineItem item, Order order) {
        try {
            Key key = new Key(order.getKey().value(), getNextSequenceNumber(order));

            return performInsert(item, key);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Long getNextSequenceNumber(Order order) {
        loadAllLinesItemsFor(order);
        Iterator it = order.getItems().iterator();
        LineItem candidate = (LineItem) it.next();

        while (it.hasNext()) {
            LineItem thisItem = (LineItem) it.next();

            if (thisItem.getKey() == null)
                continue;
            if (sequenceNumber(thisItem) > sequenceNumber(candidate))
                candidate = thisItem;
        }

        return sequenceNumber(candidate) + 1;
    }

    private static long sequenceNumber(LineItem li) {
        return sequenceNumber(li.getKey());
    }

    protected String keyTableRow() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String updateStatementString() {
        return "UPDATE line_items SET amount = ?, product = ? WHERE orderId = ? AND seq = ?";
    }

    @Override
    protected void loadUpdateStatement(DomainObjectWithKey subject, PreparedStatement stmt) throws SQLException {
        stmt.setLong(3, orderID(subject.getKey()));
        stmt.setLong(4, sequenceNumber(subject.getKey()));

        LineItem li = (LineItem) subject;

        stmt.setInt(1, li.getAmount());
        stmt.setString(2, li.getProducts());
    }

    @Override
    protected String deleteStatementString() {
        return "DELETE FROM line_items WHERE orderid = ? AND seq = ?";
    }

    @Override
    protected void loadDeleteStatement(DomainObjectWithKey subject, PreparedStatement stmt) throws SQLException {
        stmt.setLong(1, orderID(subject.getKey()));
        stmt.setLong(2, sequenceNumber(subject.getKey()));
    }
}
