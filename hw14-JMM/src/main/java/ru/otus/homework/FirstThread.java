package ru.otus.homework;

public class FirstThread implements Runnable {
    private static volatile boolean workNotAllowed;
    private final Count count;
    private boolean increase;
    private boolean decrease;

    public FirstThread(Count count) {
        workNotAllowed = true;
        increase = true;
        decrease = false;
        this.count = count;
    }

    public static boolean isWorkNotAllowed() {
        return workNotAllowed;
    }

    public static void setWorkNotAllowed(boolean workNotAllowed) {
        FirstThread.workNotAllowed = workNotAllowed;
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
                        workNotAllowed = false;
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
                        workNotAllowed = false;
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
