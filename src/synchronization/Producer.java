package synchronization;

import java.util.Random;

public class Producer implements Runnable {
    private final static Random gen = new Random();
    private final Buffer buf;

    Producer(Buffer b) {
        buf = b;
    }

    public void run() {
        while (true) {
            try {
                String msg = "msg" + gen.nextInt(100);
                buf.put(msg);
                System.out.println("Produced :" + msg);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
