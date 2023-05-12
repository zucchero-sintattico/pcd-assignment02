package assignment02.event;

import assignment02.Statistic;
import assignment02.lib.LiveReport;
import io.vertx.core.AbstractVerticle;

@SuppressWarnings("rawtypes")
public class StatisticConsumerVerticle extends AbstractVerticle {

    private final LiveReport liveReport;
    private final int count = 0;
    
    public StatisticConsumerVerticle(final LiveReport liveReport) {
        this.liveReport = liveReport;
    }

    @Override
    public void start() {
        log("StatisticConsumerVerticle started");
        vertx.eventBus().consumer("newStatistic", message -> {
            log("StatisticConsumerVerticle received newStatistic message");
            final Statistic statistic = Statistic.fromString(message.body().toString());
            liveReport.addStatistic(statistic);
        });
        vertx.eventBus().consumer("newStatistic.completed", message -> {
            log("StatisticConsumerVerticle received newStatistic.completed message");
            liveReport.complete();
            log("StatisticConsumerVerticle sending completed message");
            vertx.eventBus().send("completed", "completed");
        });
    }

    @Override
    public void stop() {
        log("StatisticConsumerVerticle stopped");
    }

    private void log(final String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] : " + message);
    }
}
