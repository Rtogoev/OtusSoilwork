package ru.otus.homework.cassettes;

import ru.otus.homework.nominals.Nominal;
import ru.otus.homework.nominals.Rub50;

public class CassetteRub50 extends CassetteGeneral {
    @Override
    public Nominal getNominal() {
        return new Rub50();
    }
}
