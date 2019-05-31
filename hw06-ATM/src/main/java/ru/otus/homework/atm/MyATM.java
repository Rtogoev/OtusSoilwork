package ru.otus.homework.atm;

import ru.otus.homework.CashOutException;
import ru.otus.homework.banknotes.Banknote;
import ru.otus.homework.cassettes.EmptyCasseteException;
import ru.otus.homework.dispensers.DispenserRub;

import java.util.List;

public class MyATM implements ATM {

    public MyATM(DispenserRub dispenser) {
        this.dispenser = dispenser;
    }

    private DispenserRub dispenser;

    @Override
    public int cashIn(List<Banknote> banknotes) {
        return dispenser.putIntoBuckets(banknotes);
    }

    @Override
    public List<Banknote> cashOut(int sum) throws CashOutException, EmptyCasseteException {
        return dispenser.getBanknotes(sum);
    }

    @Override
    public int showBalance() {
        return dispenser.getBalance();
    }
}
