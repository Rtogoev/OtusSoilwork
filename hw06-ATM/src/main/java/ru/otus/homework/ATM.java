package ru.otus.homework;

import java.util.*;

public class ATM {

    Map<Integer, Integer> buckets = new HashMap<>();

    public int cashIn(Cash cash) {
        int cashInNominal = cash.getNominal();
        int cashInAmount = cash.getAmount();
        buckets.merge(cashInNominal, cashInAmount, Integer::sum);
        return cashInAmount * cashInNominal;
    }

    public List<Cash> cashOut(int money) throws CashOutErrorException {
        List<Cash> cashOut = new LinkedList<>();

        Set<Integer> nominates = new HashSet(buckets.keySet());
        while (nominates.size() != 0 && money != 0) {
            int nominalMax = Collections.max(nominates);
            nominates.remove(nominalMax);
            int banknotes = money / nominalMax;
            if (banknotes == 0) {
                continue;
            }
            money = money - banknotes * nominalMax;
            cashOut.add(new Cash(nominalMax, banknotes));
        }

        if (money != 0) {
            throw new CashOutErrorException();
        }

        return cashOut;
    }

    public Integer showBalance() {
        return buckets.keySet()
                .stream()
                .mapToInt(
                        nominal -> nominal * buckets.get(nominal)
                ).sum();
    }
}
