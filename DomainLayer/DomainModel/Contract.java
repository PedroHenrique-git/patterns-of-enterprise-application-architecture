package DomainLayer.DomainModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Contract {
    private List revenueRecognitions = new ArrayList<>();
    private Product product;
    private Money revenue;
    private Date whenSigned;
    private Long id;

    public Contract(Product product, Money revenue, Date whenSigned) {
        this.product = product;
        this.revenue = revenue;
        this.whenSigned = whenSigned;
    }

    public Money recognizedRevenue(Date asOf) {
        Money result = Money.dollars(0);

        Iterator it = revenueRecognitions.iterator();

        while (it.hasNext()) {
            RevenueRecognition r = (RevenueRecognition) it.next();

            if (r.isRecognizableBy(asOf)) {
                result = result.add(r.getAmount());
            }
        }

        return result;
    }

    public void calculateRecognitions() {
        product.calculateRevenueRecognitions(this);
    }

    public void addRevenueRecognition(RevenueRecognition revenueRecognition) {
        revenueRecognitions.add(revenueRecognition);
    }

    public Money getRevenue() {
        return revenue;
    }

    public Date getWhenSigned() {
        return whenSigned;
    }
}
