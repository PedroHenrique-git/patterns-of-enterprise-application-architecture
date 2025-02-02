package DomainLayer.DomainModel;

import java.sql.Date;

public class RevenueRecognition {
    private Money amount;
    private Date date;

    public RevenueRecognition(Money amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    public Money getAmount() {
        return amount;
    }

    boolean isRecognizableBy(Date asOf) {
        return asOf.after(date) || asOf.equals(date);
    }
}
