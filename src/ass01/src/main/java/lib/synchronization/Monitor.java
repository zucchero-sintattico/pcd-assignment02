package assignment02.lib.synchronization;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * An abstract class that provides a monitor based on a mutex.
 * Classes that extend this class can use the methods {@link #monitored(Runnable)} and
 * {@link #monitored(Supplier)} to execute code in a synchronized manner.
 * They can also use the method {@link #newCondition()} to create new conditions.
 */
public abstract class Monitor {
    private final Lock mutex = new ReentrantLock();

    protected final Condition newCondition() {
        return mutex.newCondition();
    }

    protected final void monitored(final Runnable f) {
        mutex.lock();
        f.run();
        mutex.unlock();
    }

    protected final <A> A monitored(final Supplier<A> f) {
        mutex.lock();
        final A res = f.get();
        mutex.unlock();
        return res;
    }
}