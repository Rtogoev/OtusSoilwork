package ru.otus.homework;

public class FirstThread implements Runnable {
    private static volatile boolean increase;
    private static volatile boolean decrease;
    private final Count count;

    public FirstThread(Count count) {
        increase = true;
        decrease = false;
        this.count = count;
    }

    private void incrementAndPrint() {
        System.out.print("[" + count.incrementAndGet() + " ");
    }

    private void decreaseAndPrint() {
        System.out.print("[" + count.decreaseAndGet() + " ");
    }

    @Override
    public void run() {
        try {

            while (Main.isWork()) {

                while (increase) {
                    Thread.sleep(250);
                    if (count.getValue() > 9) {
                        increase = false;
                        decrease = true;
                        break;
                    }

                    synchronized (count) {
                        incrementAndPrint();
                        count.notify();
                    }
                }

                while (decrease) {
                    Thread.sleep(250);
                    if (count.getValue() < 2) {
                        increase = true;
                        decrease = false;
                        break;
                    }

                    synchronized (count) {
                        decreaseAndPrint();
                        count.notify();
                    }
                }

                System.out.println();

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
