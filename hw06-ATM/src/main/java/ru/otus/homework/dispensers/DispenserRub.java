package ru.otus.homework.dispensers;

import ru.otus.homework.CashOutException;
import ru.otus.homework.banknotes.Banknote;
import ru.otus.homework.cassettes.*;
import ru.otus.homework.nominals.*;

import java.util.*;

public class DispenserRub implements Dispenser {
    private final Map<Nominal, Cassette> cassettes = new HashMap<>();

    public DispenserRub() {
        cassettes.put(new Rub10(), new CassetteRub10());
        cassettes.put(new Rub50(), new CassetteRub50());
        cassettes.put(new Rub100(), new CassetteRub100());
        cassettes.put(new Rub1000(), new CassetteRub1000());
    }

    @Override
    public int putIntoBuckets(List<Banknote> banknotes) {
        return banknotes.stream()
                .mapToInt(
                        banknote -> {
                            cassettes.get(banknote.getNominal())
                                    .addBanknote(banknote);
                            return banknote.getNominal()
                                    .getDenomination();
                        }
                ).sum();
    }

    @Override
    public List<Banknote> getBanknotes(int sum) throws CashOutException, EmptyCasseteException {
        List<Banknote> banknotesCashOut = new LinkedList<>();
        Set<Nominal> nominates = new HashSet(cassettes.keySet());
        while (nominates.size() != 0 && sum != 0) {
            Nominal nominalWithMaxDenomination = findNominalWithMaxDenomination(nominates);
            nominates.remove(nominalWithMaxDenomination);
            int banknotesNeed = sum / nominalWithMaxDenomination.getDenomination();
            if (banknotesNeed == 0) {
                continue;
            }
            int banknoteAmount = cassettes.get(nominalWithMaxDenomination).getAmount();
            if (banknotesNeed - banknoteAmount >= 0) {
                sum = sum - banknoteAmount * nominalWithMaxDenomination.getDenomination();
                banknotesCashOut.addAll(getBanknotesFromCassette(nominalWithMaxDenomination, -1));
                continue;
            }
            sum = sum - banknotesNeed * nominalWithMaxDenomination.getDenomination();
            banknotesCashOut.addAll(getBanknotesFromCassette(nominalWithMaxDenomination, banknotesNeed));
        }

        if (sum != 0) {
            throw new CashOutException();
        }

        return banknotesCashOut;
    }

    /**
     * if amount = -1, return all banknotes of chosen nominal
     *
     * @param nominal
     * @param amount
     * @return
     */
    private List<Banknote> getBanknotesFromCassette(Nominal nominal, int amount) throws EmptyCasseteException {
        List<Banknote> banknotesFromCassette = new ArrayList<>();
        Cassette cassette = cassettes.get(nominal);
        amount = amount == -1 ? cassette.getAmount() : amount;
        for (int i = 0; i < amount; i++) {
            banknotesFromCassette.add(cassette.getBanknote());
        }
        return banknotesFromCassette;
    }

    private Nominal findNominalWithMaxDenomination(Set<Nominal> nominates) {
        return nominates.stream().max(Comparator.comparing(Nominal::getDenomination)).get();
    }

    @Override
    public int getBalance() {
        return cassettes.values()
                .stream()
                .mapToInt(
                        cassette -> cassette.getAmount()
                                *
                                cassette.getNominal().getDenomination()
                )
                .sum();
    }
}
