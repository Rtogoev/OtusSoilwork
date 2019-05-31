package ru.otus.homework.nominals;

import java.util.Objects;

public class Rub1000 implements Nominal {
    private final int denomination;

    public Rub1000() {
        this.denomination = 1000;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rub1000 rub1000 = (Rub1000) o;
        return denomination == rub1000.denomination;
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
