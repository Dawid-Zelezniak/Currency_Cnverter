package org.example.valueObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@NoArgsConstructor(force = true)
@EqualsAndHashCode
@ToString
public class Money {

    private static final BigDecimal CENTS = BigDecimal.valueOf(100);
    private static final BigDecimal INITIAL_VALUE = BigDecimal.ZERO;
    private static final int SCALE = 2;


    @Min(value = 0, message = "Money can not be lower than 0")
    private BigDecimal value = INITIAL_VALUE;

    public Money(BigDecimal value) {
        this.value = value;
    }

    @JsonIgnore
    public double getDoubleValue() {
        return value.doubleValue();
    }

    public Money divide(Money money) {
        BigDecimal divided = this.value.divide(money.value, SCALE, RoundingMode.HALF_UP);
        return new Money(divided);
    }

    public Money multiply(Money money) {
        BigDecimal multiplied = this.value.multiply(money.value);
        return new Money(round(multiplied));
    }

    public Money subtract(Money money) {
        BigDecimal subtract = this.value.subtract(money.value);
        return new Money(round(subtract));
    }

    public Money add(Money money) {
        BigDecimal subtract = this.value.add(money.value);
        return new Money(round(subtract));
    }

    @JsonIgnore
    public boolean isGreaterThanOrEqualZero() {
        int compareResult = this.value.compareTo(BigDecimal.ZERO);
        return compareResult >= 0;
    }

    private BigDecimal round(BigDecimal value) {
        return value.setScale(SCALE, RoundingMode.HALF_UP);
    }
}

