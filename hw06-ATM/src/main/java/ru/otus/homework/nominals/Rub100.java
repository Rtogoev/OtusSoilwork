package ru.otus.homework.nominals;

import java.util.Objects;

public class Rub100 implements Nominal {
    private final int denomination;

    public Rub100() {
        this.denomination = 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rub100 rub100 = (Rub100) o;
        return denomination == rub100.denomination;
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
