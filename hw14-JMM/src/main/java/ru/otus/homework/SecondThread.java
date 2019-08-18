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
                    while (FirstThread.isWorkNotAllowed()) {
                        count.wait();
                    }
                    System.out.print(count.getValue() + "]");
                    FirstThread.setWorkNotAllowed(true);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
