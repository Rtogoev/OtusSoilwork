package ru.otus.homework.cassettes;

public class Cassette {
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void addBill() {
        amount++;
    }

    public int getBill() throws EmptyCassetteException {
        if (amount == 0) {
            throw new EmptyCassetteException();
        }
        amount--;
        return 1;
    }
}