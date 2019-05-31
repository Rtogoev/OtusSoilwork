package ru.otus.homework.nominals;

import java.util.Objects;

public class Rub50 implements Nominal {
    private final int denomination;

    public Rub50() {
        this.denomination = 50;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rub50 rub50 = (Rub50) o;
        return denomination == rub50.denomination;
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
