package lib.synchronization;

public interface Barrier {
    void hitAndWaitAll() throws InterruptedException;
}
