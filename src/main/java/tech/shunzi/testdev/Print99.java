package tech.shunzi.testdev;

import java.util.concurrent.atomic.AtomicInteger;

public class Print99 implements Runnable {

    private int start;
    private int end;
    private Object lock;
    private static AtomicInteger count = new AtomicInteger(0);

    public Print99(int start, int end, Object lock) {
        this.start = start;
        this.end = end;
        this.lock = lock;
    }

    public static void main(String[] args) {
        Object lock = new Object();
        Thread threadOne = new Thread(new Print99(1, 3, lock));
        Thread threadTwo = new Thread(new Print99(4, 6, lock));
        Thread threadThree = new Thread(new Print99(7, 9, lock));
        threadThree.start();

        threadTwo.start();
        threadOne.start();

    }

    @Override
    public void run() {
        synchronized (lock) {
            while (count.get() < 3) {
                lock.notifyAll();
                if ((start - 1) / 3 != count.get()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    printTable(start, end);
                    count.getAndIncrement();
                }
            }
        }
    }

    private void printTable(int start, int end) {
        for (int i = start; i <= end; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i + "*" + j + "=" + i * j + "\t");
            }
            System.out.println();

        }
    }
}
