package DomainLayer.TransactionScript;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecognitionService {
    private Gateway db = new Gateway();

    public String recognizedRevenue(long contractNumber, Date asOf) {
        Money result = Money.dollars(0);

        try {
            ResultSet rs = db.findRecognitionsFor(contractNumber, asOf);

            while (rs.next()) {
                result = result.add(Money.dollars(rs.getBigDecimal("amount")));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void calculateRevenueRecognition(long contractNumber) {
        try {
            ResultSet contracts = db.findContract(contractNumber);

            contracts.next();

            Money totalRevenue = Money.dollars(contracts.getBigDecimal("revenue"));
            Date recognitionDate = contracts.getDate("dataSigned");
            String type = contracts.getString("type");

            if(type.equals("S")) {
                Money[] = allocation = totalRevenue.allocate(3);

                db.insertRecognition(contractNumber, allocation[0], recognitionDate);
                db.insertRecognition(contractNumber, allocation[1], recognitionDate.toLocalDate().plusDays(60));
                db.insertRecognition(contractNumber, allocation[2], recognitionDate.toLocalDate().plusDays(90));
            } else if(type.equals("W")) {
                db.insertRecognition(contractNumber, totalRevenue, recognitionDate);
            } else if(type.equals("D")) {
                Money[] allocation = totalRevenue.allocate(3);

                db.insertRecognition(contractNumber, allocation[0], recognitionDate);
                db.insertRecognition(contractNumber, allocation[1], recognitionDate.toLocalDate().plusDays(30));
                db.insertRecognition(contractNumber, allocation[2], recognitionDate.toLocalDate().plusDays(60));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
