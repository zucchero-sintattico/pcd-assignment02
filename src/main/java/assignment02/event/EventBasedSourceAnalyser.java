package assignment02.event;

import assignment02.SourceAnalyzer;
import assignment02.lib.report.LiveReport;
import assignment02.lib.report.LiveReportImpl;
import assignment02.lib.report.ReportConfiguration;
import io.vertx.core.Vertx;

import java.nio.file.Path;

public class EventBasedSourceAnalyser implements SourceAnalyzer {
    private final LiveReport liveReport = new LiveReportImpl();

    public EventBasedSourceAnalyser(final ReportConfiguration configuration) {
        this.liveReport.setReportConfiguration(configuration);
    }

    @Override
    public LiveReport analyzeSources(final Path directory) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new VertxCloserVerticle());
        vertx.deployVerticle(new StatisticConsumerVerticle(liveReport));
        vertx.deployVerticle(new PathConsumerVerticle());
        vertx.deployVerticle(new PathProducerVerticle(directory.toString()));
        return liveReport;
    }


}
