package ru.otus.homework.banknotes;

import ru.otus.homework.nominals.Nominal;
import ru.otus.homework.nominals.Rub50;

public class BanknoteRub50 implements Banknote {
    private final Nominal nominal = new Rub50();
    @Override
    public Nominal getNominal() {
        return nominal;
    }
}
