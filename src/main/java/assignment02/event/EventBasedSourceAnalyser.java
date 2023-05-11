package assignment02.event;

import assignment02.lib.LiveReport;
import assignment02.lib.LiveReportImpl;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import assignment02.SourceAnalyzer;

import java.nio.file.Path;

public class EventBasedSourceAnalyser implements SourceAnalyzer {
    private LiveReport liveReport = new LiveReportImpl();
    private final Vertx vertx = Vertx.vertx();
    private final Verticle pathProducerVerticle = new PathProducerVerticle();
    private final Verticle pathConsumerVerticle = new PathConsumerVerticle();


    @Override
    public LiveReport analyzeSources(final Path directory) {
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


        return liveReport;

    }


}
