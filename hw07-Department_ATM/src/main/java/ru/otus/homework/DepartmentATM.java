package ru.otus.homework;

import ru.otus.homework.atm.ATM;

import java.util.Collections;
import java.util.List;

public class DepartmentATM {
    private List<ATM> atms;
    private List<ATM> backUp;

    public DepartmentATM(List<ATM> atms) {
        this.atms = atms;
        backUp = atms;
        Collections.copy(backUp, atms);
    }

    public int getBalance() {
        return atms.stream()
                .mapToInt(ATM::getBalance)
                .sum();
    }

    public void backUp() {
        atms = backUp;
    }
}
