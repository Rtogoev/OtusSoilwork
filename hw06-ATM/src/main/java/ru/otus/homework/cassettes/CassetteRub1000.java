package ru.otus.homework.cassettes;

import ru.otus.homework.nominals.Nominal;
import ru.otus.homework.nominals.Rub100;

public class CassetteRub1000 extends CassetteGeneral {
    @Override
    public Nominal getNominal() {
        return new Rub100();
    }
}
