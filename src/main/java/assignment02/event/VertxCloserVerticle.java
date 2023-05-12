package assignment02.event;

import assignment02.lib.LiveReport;
import io.vertx.core.AbstractVerticle;

public class VertxCloserVerticle extends AbstractVerticle {

    private final LiveReport liveReport;

    public VertxCloserVerticle(final LiveReport liveReport) {
        this.liveReport = liveReport;
    }

    @Override
    public void start() {
        System.out.println(Thread.currentThread().getName() + " VertxCloserVerticle started");
        vertx.eventBus().consumer("newStatistic.completed", message -> {
            System.out.println(Thread.currentThread().getName() + " VertxCloserVerticle: " + message.body());
            liveReport.complete();
            vertx.close();
        });
    }

    @Override
    public void stop() {
        System.out.println(Thread.currentThread().getName() + " VertxCloserVerticle stopped");
    }
}
