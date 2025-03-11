package BasePatterns.ServiceStub;

import BasePatterns.Money.Money;
import BasePatterns.Plugin.PluginFactory;
import OffileConcurrencyPatterns.CoarseGrainLock.Address;

ublic interface TaxService {
    public static final TaxService INSTANCE =
        (TaxService) PluginFactory.getPlugin(TaxService.class);
    public TaxInfo getSalesTaxInfo(String productCode, Address addr, Money saleAmount);
}
