package ru.otus.homework.cassettes;

import ru.otus.homework.nominals.Rub10;

public class CassetteRub10 extends CassetteGeneral {
    @Override
    public Rub10 getNominal() {
        return new Rub10();
    }
}
