package ru.otus.homework.banknotes;

import ru.otus.homework.nominals.Nominal;
import ru.otus.homework.nominals.Rub10;

public class BanknoteRub10 implements Banknote {
    private final Nominal nominal = new Rub10();
    @Override
    public Nominal getNominal() {
        return nominal;
    }
}
