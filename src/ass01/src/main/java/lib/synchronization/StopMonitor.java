package lib.synchronization;

public class StopMonitor extends Monitor {
    private boolean toBeStopped = false;

    public StopMonitor() {
        this.toBeStopped = false;
    }

    public void stop() {
        monitored(() -> {
            this.toBeStopped = true;
        });
    }

    public boolean hasToBeStopped() {
        return monitored(() -> this.toBeStopped);
    }

}
