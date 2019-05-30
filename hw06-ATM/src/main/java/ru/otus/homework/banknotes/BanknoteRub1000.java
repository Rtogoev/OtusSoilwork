package ru.otus.homework.banknotes;

import ru.otus.homework.nominals.Nominal;
import ru.otus.homework.nominals.Rub1000;

public class BanknoteRub1000 implements Banknote {
    private final Nominal nominal = new Rub1000();
    @Override
    public Nominal getNominal() {
        return nominal;
    }
}
