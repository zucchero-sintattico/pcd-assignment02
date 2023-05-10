package assignment02.lib.synchronization;

import java.util.concurrent.locks.Condition;

/**
 * A barrier that executes an action when all threads have hit the barrier.
 */
public class ActionBarrier extends Monitor implements Barrier {
    private final Runnable action;
    private final Condition completed = this.newCondition();
    private final int n;
    private int arrived = 0;

    public ActionBarrier(final int n, final Runnable action) {
        this.n = n;
        this.action = action;
    }

    @Override
    public void hitAndWaitAll() {
        this.monitored(() -> {
            this.arrived++;
            if (this.arrived == this.n) {
                this.completed.signalAll();
                this.action.run();
            } else {
                try {
                    this.completed.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
