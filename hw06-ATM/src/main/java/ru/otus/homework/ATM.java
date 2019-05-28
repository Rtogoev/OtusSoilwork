package ru.otus.homework;

import java.util.Map;
import java.util.TreeMap;

public class ATM {

    Map<Integer, Integer> buckets = new TreeMap();

    public void cashIn(Cash cash) {
        Integer cashInNominal = cash.getNominal();
        Integer cashInAmount = cash.getAmount();
        Integer currentAmount = buckets.get(cashInNominal);
        buckets.put(
                cashInNominal,
                currentAmount + cashInAmount
        );
    }

    public Cash cashOut(Integer money) throws CashOutErrorException {
        Cash cashOut = null;
//        buckets.keySet().forEach(nominal -> nominal);
        if (cashOut == null) {
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
