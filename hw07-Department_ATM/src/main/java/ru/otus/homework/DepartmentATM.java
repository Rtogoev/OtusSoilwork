package ru.otus.homework;

import ru.otus.homework.atm.ATM;
import ru.otus.homework.dispensers.DispenserImpl;

import java.util.List;

public class DepartmentATM {
    private List<ATM> atms;
    private InMemoryDB backUp;

    public DepartmentATM(List<ATM> atms) {
        this.atms = atms;

        backUp = new InMemoryDB();
        for (ATM atm : atms) {
            atm.updateStartState();
            backUp.create(
                    new AtmMemento(
                            atm.getStartState()
                    )
            );
        }
    }

    public int getBalance() {
        return atms.stream()
                .mapToInt(ATM::getBalance)
                .sum();
    }

    public void backUp() {
        for (int i = 0; i < atms.size(); i++) {
            atms.get(i).setDispenser(
                    new DispenserImpl(
                            backUp.read(i).getStartState()
                    )
            );
        }
    }
}
