package ru.otus.homework.nominals;

import java.util.Objects;

public class Rub10 implements Nominal {
    private int denomination;

    public Rub10() {
        this.denomination = 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rub10 rub10 = (Rub10) o;
        return denomination == rub10.denomination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination);
    }

    @Override
    public int getDenomination() {
        return denomination;
    }
}
