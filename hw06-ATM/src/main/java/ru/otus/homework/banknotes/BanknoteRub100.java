package ru.otus.homework.banknotes;

import ru.otus.homework.nominals.Nominal;
import ru.otus.homework.nominals.Rub100;

public class BanknoteRub100 implements Banknote {
    private final Nominal nominal = new Rub100();
    @Override
    public Nominal getNominal() {
        return nominal;
    }
}
