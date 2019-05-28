package ru.otus.homework;

public class Cash {
    private Integer nominal;
    private Integer amount;

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
