package ru.otus.homework.dispensers;

import ru.otus.homework.CashOutException;
import ru.otus.homework.bills.Bill;
import ru.otus.homework.cassettes.Cassette;
import ru.otus.homework.cassettes.EmptyCassetteException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import static ru.otus.homework.bills.Bill.*;

public class DispenserRub implements Dispenser {
    private final Map<Bill, Cassette> cassettes = new HashMap<>();

    public DispenserRub() {
        cassettes.put(RUB10, new Cassette());
        cassettes.put(RUB50, new Cassette());
        cassettes.put(RUB100, new Cassette());
        cassettes.put(RUB200, new Cassette());
        cassettes.put(RUB500, new Cassette());
        cassettes.put(RUB1000, new Cassette());
        cassettes.put(RUB2000, new Cassette());
        cassettes.put(RUB5000, new Cassette());
    }

    @Override
    public int putIntoBuckets(Map<Bill, Integer> bills) {
        int sum = 0;
        for (Map.Entry<Bill, Integer> entry : bills.entrySet()) {
            Bill bill = entry.getKey();
            Integer amount = entry.getValue();
            for (int i = 0; i < amount; i++) {
                cassettes.get(bill).addBill();
            }
            sum = sum + bill.getNominal() * amount;
        }
        return sum;
    }

    @Override
    public Map<Bill, Integer> getBills(int sum) throws CashOutException, EmptyCassetteException {
        Map<Bill,Integer> BillsCashOut = new HashMap<>();
        TreeSet<Bill> nominates = new TreeSet<>(cassettes.keySet());
        while (nominates.size() != 0 && sum != 0) {
            Bill billMaxNominal = nominates.pollLast();
            int billsNeed = sum / billMaxNominal.getNominal();
            if (billsNeed == 0) {
                continue;
            }
            int billAmount = cassettes.get(billMaxNominal).getAmount();
            if (billsNeed - billAmount >= 0) {
                sum = sum - billAmount * billMaxNominal.getNominal();
                BillsCashOut.put(
                        billMaxNominal,
                        getBillsFromCassette(
                                billMaxNominal,
                                -1
                        )
                );
                continue;
            }
            sum = sum - billsNeed * billMaxNominal.getNominal();
            BillsCashOut.put(
                    billMaxNominal,
                    getBillsFromCassette(
                            billMaxNominal,
                            billsNeed
                    )
            );
        }

        if (sum != 0) {
            throw new CashOutException();
        }

        return BillsCashOut;
    }

    /**
     * if amount = -1, return all bills of chosen nominal
     */
    private int getBillsFromCassette(Bill bill, int amount) throws EmptyCassetteException {
        Cassette cassette = cassettes.get(bill);
        amount = amount == -1 ? cassette.getAmount() : amount;
        for (int i = 0; i < amount; i++) {
            cassette.getBill();
        }
        return amount;
    }

    @Override
    public int getBalance() {
        int sum = 0;
        for (Map.Entry<Bill, Cassette> entry : cassettes.entrySet()) {
            sum = sum + entry.getKey().getNominal() * entry.getValue().getAmount();
        }
    return sum;
    }
}
