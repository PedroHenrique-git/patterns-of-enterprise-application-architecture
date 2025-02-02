package DomainLayer.ServiceLayer;

import java.sql.Date;

import DomainLayer.DomainModel.Contract;

public class RecognitionService extends ApplicationService {
    public void calculateRevenueRecognitions(long contractNumber) {
        Contract contract = Contract.readForUpdate(contractNumber);
        contract.calculateRecognitions();

        getEmailGateway().sendEmailMessage(
                contract.getAdministratorEmail(),
                "RE Contract #" + contractNumber,
                contract + " has had revenue recognitions calculated.");

        getIntegrationGateway().publishRevenueRecognition(contract);
    }

    public Money recognizedRevenue(long contractNumber, Date asof) {
        return Contract.read(contractNumber).recognizedRevenue(asof);
    }
}
