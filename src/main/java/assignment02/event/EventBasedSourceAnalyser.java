package assignment02.event;

import assignment02.lib.LiveReport;
import assignment02.lib.LiveReportImpl;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import assignment02.SourceAnalyzer;

import java.nio.file.Path;

public class EventBasedSourceAnalyser implements SourceAnalyzer {
    LiveReport liveReport = new LiveReportImpl();

    @Override
    public LiveReport analyzeSources(final Path directory) {
        Vertx vertx = Vertx.vertx();
        Verticle pathProducerVerticle = new PathProducerVerticle();
        Verticle pathConsumerVerticle = new PathConsumerVerticle();

        Future<String> producerId = vertx.deployVerticle(pathProducerVerticle)
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        System.out.println("Deployment id is: " + ar.result());
                    } else {
                        System.out.println("Deployment failed!");
                    }
                });

        Future<String> consumerId = vertx.deployVerticle(pathConsumerVerticle)
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        System.out.println("Deployment id is: " + ar.result());
                    } else {
                        System.out.println("Deployment failed!");
                    }
                });

        vertx.close();

        return liveReport;

    }


}
