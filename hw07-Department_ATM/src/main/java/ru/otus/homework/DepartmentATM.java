package ru.otus.homework;

import ru.otus.homework.atm.ATM;
import ru.otus.homework.bills.Bill;
import ru.otus.homework.cassettes.Cassette;
import ru.otus.homework.dispensers.DispenserImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentATM {
    private List<ATM> atms;
    private InMemoryDB backUp; // TODO вот тут мементо? отдельный класс, в котором копии банокматов. Делается копия мапы и новый банкос

    public DepartmentATM(List<ATM> atms) {
        this.atms = atms;

        backUp = new InMemoryDB();
        for (ATM atm : atms) {
            Map<Bill, Cassette> startState = new HashMap<>();
            Map<Bill, Cassette> cassettes = atm.getDispenser().getCassettes();
            cassettes.forEach(
                    (bill, cassette) -> startState.put(
                            bill,
                            new Cassette(cassette.getAmount())
                    )
            );
            backUp.create(new StartStateMemento(startState));
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
