package ru.otus.homework.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.CashOutException;
import ru.otus.homework.banknotes.*;
import ru.otus.homework.cassettes.EmptyCasseteException;
import ru.otus.homework.dispensers.DispenserRub;
import ru.otus.homework.nominals.Rub10;
import ru.otus.homework.nominals.Rub100;
import ru.otus.homework.nominals.Rub1000;
import ru.otus.homework.nominals.Rub50;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyATMTest {
    private static int amountBanknoteRub10 = 12;
    private static int amountBanknoteRub50 = 64;
    private static int amountBanknoteRub100 = 23;
    private static int amountBanknoteRub1000 = 734;
    private MyATM myATM;
    private List<Banknote> banknotesCashIn;
    private List<Banknote> expectedBanknotesCashOut;
    private int expectedSum;

    @BeforeEach
    void setUp() {
        myATM = new MyATM(new DispenserRub());
        banknotesCashIn = new ArrayList<>();
        expectedBanknotesCashOut = new ArrayList<>();
        for (int i = 0; i < amountBanknoteRub10; i++) {
            banknotesCashIn.add(new BanknoteRub10());
        }

        for (int i = 0; i < amountBanknoteRub50; i++) {
            banknotesCashIn.add(new BanknoteRub50());
        }

        for (int i = 0; i < amountBanknoteRub100; i++) {
            banknotesCashIn.add(new BanknoteRub100());
        }

        for (int i = 0; i < amountBanknoteRub1000; i++) {
            banknotesCashIn.add(new BanknoteRub1000());
        }
        expectedBanknotesCashOut.addAll(banknotesCashIn);
        expectedSum =
                amountBanknoteRub10 * new Rub10().getDenomination() +
                        amountBanknoteRub50 * new Rub50().getDenomination() +
                        amountBanknoteRub100 * new Rub100().getDenomination() +
                        amountBanknoteRub1000 * new Rub1000().getDenomination();
    }

    @Test
    void cashIn() {
        assertEquals(expectedSum, myATM.cashIn(banknotesCashIn));
    }

    @Test
    void cashIn_10() {
        List<Banknote> banknoteRub10 = new ArrayList<>();
        banknoteRub10.add(new BanknoteRub10());
        assertEquals(10, myATM.cashIn(banknoteRub10));
    }

    @Test
    void cashIn_50() {
        List<Banknote> banknoteRub50 = new ArrayList<>();
        banknoteRub50.add(new BanknoteRub50());
        assertEquals(50, myATM.cashIn(banknoteRub50));
    }

    @Test
    void cashIn_100() {
        List<Banknote> banknoteRub100 = new ArrayList<>();
        banknoteRub100.add(new BanknoteRub100());
        assertEquals(100, myATM.cashIn(banknoteRub100));
    }

    @Test
    void cashIn_1000() {
        List<Banknote> banknoteRub1000 = new ArrayList<>();
        banknoteRub1000.add(new BanknoteRub1000());
        assertEquals(1000, myATM.cashIn(banknoteRub1000));
    }


    @Test
    void cashOut_all() throws CashOutException, EmptyCasseteException {
        myATM.cashIn(banknotesCashIn);
        List<Banknote> actualBanknotesCashOut = myATM.cashOut(expectedSum);
        assertEquals(
                expectedBanknotesCashOut.size(),
                actualBanknotesCashOut.size()
        );
        int actualAmountBanknoteRub10 = 0;
        int actualAmountBanknoteRub50 = 0;
        int actualAmountBanknoteRub100 = 0;
        int actualAmountBanknoteRub1000 = 0;
        for (Banknote banknote : actualBanknotesCashOut) {
            if (banknote.getNominal().equals(new Rub10())) {
                actualAmountBanknoteRub10++;
                continue;
            }
            if (banknote.getNominal().equals(new Rub50())) {
                actualAmountBanknoteRub50++;
                continue;
            }
            if (banknote.getNominal().equals(new Rub100())) {
                actualAmountBanknoteRub100++;
                continue;
            }
            if (banknote.getNominal().equals(new Rub1000())) {
                actualAmountBanknoteRub1000++;
            }
        }
        assertEquals(amountBanknoteRub10, actualAmountBanknoteRub10);
        assertEquals(amountBanknoteRub50, actualAmountBanknoteRub50);
        assertEquals(amountBanknoteRub100, actualAmountBanknoteRub100);
        assertEquals(amountBanknoteRub1000, actualAmountBanknoteRub1000);
    }

    @Test
    void cashOut_10() throws CashOutException, EmptyCasseteException {
        List<Banknote> banknoteRub10 = new ArrayList<>();
        banknoteRub10.add(new BanknoteRub10());
        myATM.cashIn(banknoteRub10);
        assertEquals(banknoteRub10, myATM.cashOut(10));
    }

    @Test
    void cashOut_50() throws CashOutException, EmptyCasseteException {
        List<Banknote> banknoteRub50 = new ArrayList<>();
        banknoteRub50.add(new BanknoteRub50());
        myATM.cashIn(banknoteRub50);
        assertEquals(banknoteRub50, myATM.cashOut(50));
    }

    @Test
    void cashOut_100() throws CashOutException, EmptyCasseteException {
        List<Banknote> banknoteRub100 = new ArrayList<>();
        banknoteRub100.add(new BanknoteRub100());
        myATM.cashIn(banknoteRub100);
        assertEquals(banknoteRub100, myATM.cashOut(100));
    }

    @Test
    void cashOut_1000() throws CashOutException, EmptyCasseteException {
        List<Banknote> banknoteRub1000 = new ArrayList<>();
        banknoteRub1000.add(new BanknoteRub1000());
        myATM.cashIn(banknoteRub1000);
        assertEquals(banknoteRub1000, myATM.cashOut(1000));
    }

    @Test
    void cashOut_Error_smaller() throws CashOutException, EmptyCasseteException {
        List<Banknote> banknoteRub1000 = new ArrayList<>();
        banknoteRub1000.add(new BanknoteRub1000());
        myATM.cashIn(banknoteRub1000);
        assertThrows(CashOutException.class, () -> myATM.cashOut(1));
    }

    @Test
    void cashOut_Error_grater() throws CashOutException, EmptyCasseteException {
        List<Banknote> banknoteRub10 = new ArrayList<>();
        banknoteRub10.add(new BanknoteRub10());
        myATM.cashIn(banknoteRub10);
        assertThrows(CashOutException.class, () -> myATM.cashOut(100));
    }

    @Test
    void showBalance() {
        assertEquals(expectedSum, myATM.cashIn(banknotesCashIn));
        assertEquals(expectedSum, myATM.showBalance());
    }
}