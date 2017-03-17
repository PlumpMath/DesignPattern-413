package synchronization;

public class Consumer implements Runnable {
    private Buffer buf;

    Consumer(Buffer b) {
        buf = b;
    }

    public void run() {
        while (true) {
            try {
                String msg = buf.take();
                System.out.println("Consumed :" + msg);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
