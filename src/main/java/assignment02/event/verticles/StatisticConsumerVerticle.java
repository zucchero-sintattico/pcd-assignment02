package assignment02.event.verticles;

import assignment02.lib.report.ReportAsyncBuilder;
import assignment02.lib.report.Statistic;
import io.vertx.core.AbstractVerticle;

@SuppressWarnings("rawtypes")
public class StatisticConsumerVerticle extends AbstractVerticle {

    private final ReportAsyncBuilder liveReport;
    private final int count = 0;

    public StatisticConsumerVerticle(final ReportAsyncBuilder liveReport) {
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
        vertx.eventBus().consumer("statisticsGeneration.completed", message -> {
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
