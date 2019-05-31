package ru.otus.homework.atm;

import ru.otus.homework.CashOutException;
import ru.otus.homework.banknotes.Banknote;
import ru.otus.homework.cassettes.EmptyCasseteException;

import java.util.List;

public interface ATM {

    int cashIn(List<Banknote> banknotes);

    List<Banknote> cashOut(int money) throws CashOutException, EmptyCasseteException;

    int showBalance();
}
