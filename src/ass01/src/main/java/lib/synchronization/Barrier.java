package assignment02.lib.synchronization;

public interface Barrier {
    void hitAndWaitAll() throws InterruptedException;
}
