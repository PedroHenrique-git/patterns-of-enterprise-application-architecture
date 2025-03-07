package OffileConcurrencyPatterns.OptimisticOfflineLock;

import java.sql.ResultSet;
import java.sql.SQLException;

import ObjectRelationalStructural.SerializedLOB.Customer;

public class CustomerMapper extends AbstractMapper {
    @Override
    protected DomainObject load(Long id, ResultSet rs) throws SQLException {
        String name = rs.getString(2);

        return Customer.activate(id, name, addresses);
    }
}
