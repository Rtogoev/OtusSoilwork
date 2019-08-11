package ru.otus.homework;

public class SecondThread implements Runnable {
    private final Count count;

    public SecondThread(Count count) {
        this.count = count;
    }


    @Override
    public void run() {
        try {

            while (Main.isWork()) {
                synchronized (count) {
                    count.wait();
                    System.out.print(count.getValue() + "]");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
