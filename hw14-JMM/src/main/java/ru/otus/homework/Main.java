package ru.otus.homework;

public class Main {

    private static volatile boolean work = true;

    public static void main(String[] args) throws InterruptedException {
        Count count = new Count(0);
        FirstThread firstThread = new FirstThread(count);
        SecondThread secondThread = new SecondThread(count);
        new Thread(secondThread).start();
        new Thread(firstThread).start();
        Thread.sleep(30000);
        work = false;
    }


    public static boolean isWork() {
        return work;
    }
}
