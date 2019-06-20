package ru.otus.homework;

import ru.otus.homework.atm.ATM;
import ru.otus.homework.dispensers.DispenserImpl;

import java.util.List;

public class DepartmentATM {
    private List<ATM> atms;
    private InMemoryDB backUp;

    public DepartmentATM(List<ATM> atms) {
        this.atms = atms;
        atms.forEach(ATM::updateStartState);
    }

    public int getBalance() {
        return atms.stream()
                .mapToInt(ATM::getBalance)
                .sum();
    }

    public void backUp() {
        for (int i = 0; i < atms.size(); i++) {
            ATM atm = atms.get(i);
            atm.setDispenser(
                    new DispenserImpl(
                            atm.getStartState()
                    )
            );
        }
    }
}
