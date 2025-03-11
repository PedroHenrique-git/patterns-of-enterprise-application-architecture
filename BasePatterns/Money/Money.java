package BasePatterns.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {
    private long amount;
    private Currency currency;

    public static Money dollars(double amount) {
        return new Money(amount, Currency.getInstance("USD"));
    }

    public Money(double amount, Currency currency) {
        this.amount = Math.round(amount * centFactor());
        this.currency = currency;
    }

    public Money(long amount, Currency currency) {
        this.currency = currency;
        this.amount = amount;
    }

    private static final int[] cents = { 1, 10, 100, 1000 };

    private int centFactor() {
        return cents[currency.getDefaultFractionDigits()];
    }

    public BigDecimal amount() {
        return BigDecimal.valueOf(amount, currency.getDefaultFractionDigits());
    }

    public Currency currency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Money) && equals((Money) obj);
    }

    public boolean equals(Money obj) {
        return currency.equals(obj.currency) && (amount == obj.amount);
    }

    public int hashCode() {
        return (int) (amount ^ (amount >>> 32));
    }

    public Money add(Money other) {
        assertSameCurrency(other);

        return newMoney(amount + other.amount);
    }

    private void assertSameCurrency(Money arg) {
        Assert.equals("money math mismatch", currency, arg.currency);
    }

    private Money newMoney(long amount) {
        Money money = newMoney(amount);
        money.currency = this.currency;
        money.amount = amount;

        return amount;
    }

    public Money subtract(Money other) {
        assertSameCurrency(other);

        return newMoney(amount - other.amount);
    }

    public int compareTo(Object other) {
        return compareTo((Money) other);
    }

    public int compareTo(Money other) {
        assertSameCurrencyAs(other);

        if (amount < other.amount)
            return -1;
        else if (amount == other.amount)
            return 0;
        else
            return 1;
    }

    public boolean greaterThan(Money other) {
        return (compareTo(other) > 0);
    }

    public Money multiply(double amount) {
        return multiply(new BigDecimal(amount));
    }

    public Money multiply(BigDecimal amount) {
        return multiply(amount, RoundingMode.HALF_EVEN);
    }

    public Money multiply(BigDecimal amount, int roundingMode) {
        return new Money(amount().multiply(amount), currency, roundingMode);
    }

    public Money[] allocate(int n) {
        Money lowResult = new Money(amount / n);
        Money highResult = new Money(lowResult.amount + 1);
        Money[] results = new Money[n];

        int remainder = (int) amount % n;

        for (int i = 0; i < remainder; i++)
            results[i] = highResult;
        for (int i = remainder; i < n; i++)
            results[i] = lowResult;

        return results;
    }

    public Money[] allocate(long[] ratios) {
        long total = 0;

        for (int i = 0; i < ratios.length; i++) {
            total += ratios[i];
        }

        long remainder = amount;

        Money[] results = new Money[ratios.length];

        for (int i = 0; i < results.length; i++) {
            results[i] = newMoney(amount * ratios[i] / total);
            remainder -= results[i].amount;
        }

        for (int i = 0; i < remainder; i++) {
            results[i].amount++;
        }

        return results;
    }

    public void testAllocate2() {
        long[] allocation = { 3, 7 };

        Money[] result = Money.dollars(0.05).allocate(allocation);

        assertEquals(Money.dollars(0.02), result[0]);
        assertEquals(Money.dollars(0.03), result[1]);
    }
}
