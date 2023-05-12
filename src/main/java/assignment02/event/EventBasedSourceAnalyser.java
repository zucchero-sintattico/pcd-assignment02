package assignment02.event;

import assignment02.SourceAnalyzer;
import assignment02.lib.LiveReport;
import assignment02.lib.LiveReportImpl;
import assignment02.lib.ReportConfiguration;
import io.vertx.core.Vertx;

import java.nio.file.Path;

public class EventBasedSourceAnalyser implements SourceAnalyzer {
    private final ReportConfiguration configuration;
    private final LiveReport liveReport = new LiveReportImpl();

    public EventBasedSourceAnalyser(final ReportConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public LiveReport analyzeSources(final Path directory) {
        this.liveReport.setReportConfiguration(this.configuration);

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new VertxCloserVerticle());
        vertx.deployVerticle(new StatisticConsumerVerticle(this.liveReport));
        vertx.deployVerticle(new PathConsumerVerticle());
        vertx.deployVerticle(new PathProducerVerticle(directory.toString()));

        return liveReport;
    }


}
