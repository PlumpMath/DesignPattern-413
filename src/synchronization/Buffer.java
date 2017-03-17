package synchronization;

public interface Buffer {
    void put(String str) throws InterruptedException;

    String take() throws InterruptedException;
}
