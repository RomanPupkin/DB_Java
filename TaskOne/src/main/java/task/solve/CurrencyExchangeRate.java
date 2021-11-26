package task.solve;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Currency;

public class CurrencyExchangeRate {

    private BigDecimal rate;
    private Currency from;
    private Currency to;

    public CurrencyExchangeRate(BigDecimal rate, Currency from, Currency to) {
        this.from = Currency.getInstance(from.getCurrencyCode());
        this.to = Currency.getInstance(to.getCurrencyCode());
        this.rate = new BigDecimal(rate.toString(), MathContext.UNLIMITED); //rate.doubleValue()).setScale(to.getDefaultFractionDigits(), RoundingMode.HALF_UP);
    }

    public Money convert(Money m) {
        if (m.getCurrency().equals(from)) {
            return new Money(to, m.getAmount().multiply(rate));
        }
        throw new IncorrectExchangeRateException("convert different currencies");
    }
}
