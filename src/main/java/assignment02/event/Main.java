package assignment02.event;

import assignment02.SourceAnalyzer;
import assignment02.lib.ReportConfiguration;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyzer eventBasedSourceAnalyser = new EventBasedSourceAnalyser(configuration);
        eventBasedSourceAnalyser.getReport(Path.of("src")).thenAccept(System.out::println);
    }
}
