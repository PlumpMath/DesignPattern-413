package synchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerPattern {
    public static void main(String[] args) {
        ExecutorService app = Executors.newCachedThreadPool();
        Buffer buf = new SynchronizedBuffer();
        app.execute(new Producer(buf));
        app.execute(new Consumer(buf));
        app.shutdown();
    }
}
