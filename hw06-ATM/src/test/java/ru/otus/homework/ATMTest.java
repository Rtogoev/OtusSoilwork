package ru.otus.homework;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ATMTest {

    ATM atm = new ATM();

    @Test
    void cashIn() {
        assertEquals(
                100,
                atm.cashIn(
                        new Cash(10, 10)
                )
        );
        assertEquals(
                500,
                atm.cashIn(
                        new Cash(50, 10)
                )
        );
        assertEquals(
                1000,
                atm.cashIn(
                        new Cash(100, 10)
                )
        );
    }

    @Test
    void cashOut_SUCCESS() throws CashOutException {
        List<Cash> expectedCashOut = new LinkedList<>();
        expectedCashOut.add(new Cash(1000, 1));

        atm.cashIn(
                new Cash(10, 100)
        );
        atm.cashIn(
                new Cash(100, 10)
        );
        atm.cashIn(
                new Cash(1000, 1)
        );

        List<Cash> actualCashOut = atm.cashOut(1000);

        assertEquals(expectedCashOut, actualCashOut);
    }

    @Test
    void cashOut_FAILURE_1() {
        atm.cashIn(new Cash(10, 1));
        assertThrows(
                CashOutException.class, () -> atm.cashOut(1)
        );
    }

    @Test
    void cashOut_FAILURE_2() {
        atm.cashIn(new Cash(10, 1));
        assertThrows(
                CashOutException.class, () -> atm.cashOut(20)
        );
    }

    @Test
    void showBalance() {
        atm.cashIn(new Cash(100, 1));
        atm.cashIn(new Cash(100, 2));
        assertEquals(
                300,
                atm.showBalance()
        );
    }
}