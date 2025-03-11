package BasePatterns.ServiceStub;

import java.math.BigDecimal;

import BasePatterns.Money.Money;
import OffileConcurrencyPatterns.CoarseGrainLock.Address;

public class FlatRateTaxService implements TaxService {
    private static final BigDecimal FLAT_RATE = new BigDecimal("0.0500");

    public TaxInfo getSalesTaxInfo(String productCode, Address addr, Money saleAmount) {
        return new TaxInfo(FLAT_RATE, saleAmount.multiply(FLAT_RATE));
    }
}
