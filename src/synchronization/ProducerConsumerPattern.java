package synchronization;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Buffer {
    private String[] message = new String[3];
    private int in = 0, out = 0;

    void put(String str) {
        message[in++] = str;
        in = in % 3;
    }

    String take() {
        String msg = message[out++];
        out = out % 3;
        return msg;
    }
}

class Producer implements Runnable {
    private Buffer buf;
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;

    Producer(Buffer b, Semaphore m, Semaphore f, Semaphore e) {
        buf = b;
        full = f;
        empty = e;
        mutex = m;
    }

    public void run() {
        try {
            Random rand = new Random();
            while (true) {
                String msg = "msg" + rand.nextInt(100);
                empty.acquire();
                mutex.acquire();
                buf.put(msg);
                System.out.println("Produced :" + msg);
                mutex.release();
                full.release();
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}

class Consumer implements Runnable {
    private Buffer buf;
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;

    Consumer(Buffer b, Semaphore m, Semaphore f, Semaphore e) {
        buf = b;
        full = f;
        empty = e;
        mutex = m;
    }

    public void run() {
        try {
            while (true) {
                full.acquire();
                mutex.acquire();
                String msg = buf.take();
                System.out.println("Consumed :" + msg);
                mutex.release();
                empty.release();
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}

public class ProducerConsumerPattern {

    public static void main(String[] args) {
        Buffer buf = new Buffer();
        Semaphore empty = new Semaphore(3);
        Semaphore full = new Semaphore(0);
        Semaphore mutex = new Semaphore(1);
        Producer prod = new Producer(buf, mutex, full, empty);
        Consumer cons = new Consumer(buf, mutex, full, empty);
        (new Thread(prod)).start();
        (new Thread(cons)).start();
    }

}
