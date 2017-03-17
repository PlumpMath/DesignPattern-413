package synchronization;

public class ThreadSafeSingleton {
    private volatile ThreadSafeSingleton instance;

    public ThreadSafeSingleton getInstance() {
        ThreadSafeSingleton result = instance;
        if (result == null) {
            synchronized (this) {
                result = instance;
                if (result == null) {
                    instance = result = new ThreadSafeSingleton();
                }
            }
        }
        return result;
    }
}
