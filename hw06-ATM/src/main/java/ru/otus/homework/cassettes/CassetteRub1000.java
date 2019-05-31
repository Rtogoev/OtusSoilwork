package ru.otus.homework.cassettes;

import ru.otus.homework.nominals.Nominal;
import ru.otus.homework.nominals.Rub1000;

public class CassetteRub1000 extends CassetteGeneral {
    @Override
    public Nominal getNominal() {
        return new Rub1000();
    }
}
