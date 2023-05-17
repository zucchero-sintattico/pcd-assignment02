package assignment02.event.verticles;

import assignment02.lib.report.Statistic;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class PathConsumerVerticle extends AbstractVerticle {

    private int count = 0;

    @Override
    public void start() {
        log("PathConsumerVerticle started");
        final List<Future> filesReadFutures = new ArrayList<>();
        vertx.eventBus().consumer("newPath", message -> {
            log("PathConsumerVerticle received newPath message");
            count++;
            final Future fileReadFuture = vertx.fileSystem().readFile(message.body().toString()).onSuccess(result -> {
                int lines = result.toString().lines().toList().size();
                final Statistic statistic = new Statistic(Path.of(message.body().toString()), lines);
                log("Pushing newStatistic to event bus");
                vertx.eventBus().send("newStatistic", statistic.toString());
            });
            filesReadFutures.add(fileReadFuture);
        });
        vertx.eventBus().consumer("newPath.completed", message -> {
            CompositeFuture.all(filesReadFutures).onSuccess(x -> {
                log("PathConsumerVerticle completed, found " + count + " files");
                log("PathConsumerVerticle sending newStatistic.completed message");
                vertx.eventBus().send("newStatistic.completed", "completed");
            });
        });
    }

    @Override
    public void stop() {
        log("PathConsumerVerticle stopped");
    }

    private void log(final String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] : " + message);
    }
}
