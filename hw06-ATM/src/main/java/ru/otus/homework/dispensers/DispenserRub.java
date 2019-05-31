package ru.otus.homework.dispensers;

import ru.otus.homework.banknotes.Banknote;
import ru.otus.homework.cassettes.*;
import ru.otus.homework.nominals.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Banknote> getBanknotes(int sum) {
        return null;
    }

    @Override
    public int getBalance() {
        return 0;
    }
}
