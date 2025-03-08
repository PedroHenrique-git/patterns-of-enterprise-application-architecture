package OffileConcurrencyPatterns.PessimisticOfflineLock;

import ObjectRelationalMetadataMapping.QueryObject.Mapper;
import ObjectRelationalStructural.SerializedLOB.Customer;

public class EditCustomerCommand implements Command {
    @Override
    public void process() throws Exception {
        startNewBusinessTransaction();
        Long customerId = getReq().getParameter("customer_id");
        ExclusiveReadLockManager.INSTANCE.acquireLock(customerId, AppSessionManager.getSession().getId());
        Mapper customerMapper = MapperRegistry.INSTANCE.getMapper(Customer.class);
        Customer customer = (Customer) customerMapper.find(customerId);
        getReq().getSession().setAttribute("customer", customer);
        forward("/editCustomer.jsp");
    }
}
