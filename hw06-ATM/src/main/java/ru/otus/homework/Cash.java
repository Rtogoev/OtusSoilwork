package ru.otus.homework;

import java.util.Objects;

public class Cash {
    private Integer nominal;
    private Integer amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cash cash = (Cash) o;
        return Objects.equals(nominal, cash.nominal) &&
                Objects.equals(amount, cash.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nominal, amount);
    }

    public Cash(Integer nominal, Integer amount) {
        this.nominal = nominal;
        this.amount = amount;
    }

    public Integer getNominal() {
        return nominal;
    }

    public Integer getAmount() {
        return amount;
    }
}
