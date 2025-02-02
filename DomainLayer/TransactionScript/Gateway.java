package DomainLayer.TransactionScript;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Gateway {
    private Connection db;

    private static final String FIND_RECOGNITION_STATEMENT = "SELECT amount" +
            " FROM revenueRecognitions" +
            " WHERE contract = ? AND recognitionon <= ?";

    private static final String FIND_CONTRACTS_STATEMENT = "SELECT *" +
            " FROM contracts c, products p " +
            " WHERE ID ? AND c.product = p.ID";

    private static final String INSERT_RECOGNITION_STATEMENT = "INSERT INTO revenueRecognitions VALUES(?, ?, ? )";

    public ResultSet findRecognitionsFor(long contractID, Date asof) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(FIND_RECOGNITION_STATEMENT);

        stmt.setLong(1, contractID);
        stmt.setDate(2, asof);

        return stmt.executeQuery();
    }

    public ResultSet findContract(long contractID) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(FIND_CONTRACTS_STATEMENT);

        stmt.setLong(1, contractID);

        ResultSet result = stmt.executeQuery();

        return result;
    }

    public void insertRecognition(long contractID, Money amount, Date asof) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(INSERT_RECOGNITION_STATEMENT);

        stmt.setLong(1, contractID);
        stmt.setBigDecimal(2, amount.amount());
        stmt.setDate(3, asof);

        stmt.executeUpdate();
    }
}