package assignment02.event;

import assignment02.Statistic;
import assignment02.lib.LiveReport;
import assignment02.lib.LiveReportImpl;
import assignment02.lib.Report;
import assignment02.lib.ReportConfiguration;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import assignment02.SourceAnalyzer;

import java.nio.file.Path;

public class EventBasedSourceAnalyser implements SourceAnalyzer {
    private final ReportConfiguration configuration;
    private LiveReport liveReport = new LiveReportImpl();

    public EventBasedSourceAnalyser(final ReportConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public LiveReport analyzeSources(final Path directory) {
        this.liveReport.setReportConfiguration(this.configuration);

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new VertxCloserVerticle(this.liveReport));
        vertx.deployVerticle(new PathConsumerVerticle());
        vertx.deployVerticle(new PathProducerVerticle(directory.toString()));

        return liveReport;
    }


}
