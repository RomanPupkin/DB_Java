package task.solve;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {
    private Currency currency;
    private BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount.setScale(this.currency.getDefaultFractionDigits(), RoundingMode.HALF_UP);
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money add(Money m) {
        if (this.getCurrency() == m.getCurrency()) {
            return new Money(this.getCurrency(), this.getAmount().add(m.getAmount()));
        }
        throw new DifferentCurrenciesException("add different currencies");
    }

    public Money subtract(Money m) {
        if (this.getCurrency() == m.getCurrency()) {
            return new Money(this.getCurrency(), this.getAmount().subtract(m.getAmount()));
        }
        throw new DifferentCurrenciesException("subtract different currencies");
    }

    public Money multiply(BigDecimal ratio) {
        return new Money(this.getCurrency(), this.getAmount().multiply(ratio));//.setScale(this.getCurrency().getDefaultFractionDigits(), RoundingMode.HALF_UP)));
//        throw new NotImplementedException();
    }

    public Money divide(BigDecimal ratio) {
        if (!ratio.equals(0)) {
            return new Money(this.getCurrency(), this.getAmount().divide(ratio));//.setScale(this.getCurrency().getDefaultFractionDigits(), RoundingMode.HALF_UP)));
        }
        throw new NotImplementedException();
    }
}
