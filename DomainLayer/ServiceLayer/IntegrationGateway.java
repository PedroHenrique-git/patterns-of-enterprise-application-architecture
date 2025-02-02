package DomainLayer.ServiceLayer;

import DomainLayer.DomainModel.Contract;

public interface IntegrationGateway {
    void publishRevenueRecognition(Contract contract);
}
