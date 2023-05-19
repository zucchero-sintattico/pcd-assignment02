package assignment02.event.verticles;

import assignment02.lib.report.live.LiveReport;
import io.vertx.core.AbstractVerticle;

public class VertxCloserVerticle extends AbstractVerticle {

    private final LiveReport liveReport;

    public VertxCloserVerticle(final LiveReport liveReport) {
        this.liveReport = liveReport;
    }

    @Override
    public void start() {
        log("VertxCloserVerticle started");
        vertx.eventBus().consumer("completed", message -> {
            log("VertxCloserVerticle received completed message");
            log("VertxCloserVerticle closing vertx");
            vertx.close(e -> liveReport.complete());
        });
    }

    @Override
    public void stop() {
        log("VertxCloserVerticle stopped");
    }

    private void log(final String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] : " + message);
    }
}
