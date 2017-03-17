package synchronization;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedBuffer implements Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition empty = lock.newCondition();
    private final Condition full = lock.newCondition();
    private final int bufferSize = 3;
    private final String[] message = new String[bufferSize];
    private int in = 0, out = 0, counter = 0;

    public void put(String str) throws InterruptedException {
        lock.lock();
        while (counter == bufferSize) {
            full.await();
        }
        message[in++] = str;
        in = in % 3;
        counter++;
        empty.signalAll();
        lock.unlock();
    }

    public String take() throws InterruptedException {
        lock.lock();
        while (counter == 0) {
            empty.await();
        }
        String msg = message[out++];
        out = out % 3;
        counter--;
        full.signalAll();
        lock.unlock();
        return msg;
    }
}
