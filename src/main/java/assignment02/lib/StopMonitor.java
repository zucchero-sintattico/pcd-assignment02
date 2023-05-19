package assignment02.lib;

public class StopMonitor extends Monitor {
    private boolean toBeStopped = false;

    public StopMonitor() {
        this.toBeStopped = false;
    }

    public void stop() {
        monitored(() -> toBeStopped = true);
    }

    public boolean hasToBeStopped() {
        return monitored(this::hasToBeStopped);
    }

}
