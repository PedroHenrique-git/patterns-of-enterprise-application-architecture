package DomainLayer.ServiceLayer;

public abstract class ApplicationService {
    protected EmailGateway getEmailGateway() {
        // return an instance of EmailGateway
    }

    protected IntegrationGateway getIntegrationGateway() {
        // return an instance of IntegrationGateway
    }
}
