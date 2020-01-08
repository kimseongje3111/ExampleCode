public class Main {

    public static void main(String[] args) {
        Thread myThread1 = new MyThread1();
        Thread myThread2 = new Thread(new MyThread2());

        startThread(myThread1);
        startThread(myThread2);
    }

    private static void startThread(Thread thread) {
        thread.start();
    }
}

class MyThread1 extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(getName() + " : " + i);
        }
    }
}

class MyThread2 implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
        }
    }
}