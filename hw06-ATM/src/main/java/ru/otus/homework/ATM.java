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

    public List<Cash> cashOut(int money) throws CashOutException {
        List<Cash> cashOut = new LinkedList<>();

        Set<Integer> nominates = new HashSet(buckets.keySet());
        while (nominates.size() != 0 && money != 0) {
            int nominalMax = Collections.max(nominates);
            nominates.remove(nominalMax);
            int banknotesNeed = money / nominalMax;
            if (banknotesNeed == 0) {
                continue;
            }
            Integer banknotesOwn = buckets.get(nominalMax);
            if (banknotesNeed - banknotesOwn >= 0) {
                money = money - banknotesOwn * nominalMax;
                cashOut.add(new Cash(nominalMax, banknotesOwn));
                buckets.remove(nominalMax);
                continue;
            }
            money = money - banknotesNeed * nominalMax;
            cashOut.add(new Cash(nominalMax, banknotesNeed));
        }

        if (money != 0) {
            throw new CashOutException();
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
