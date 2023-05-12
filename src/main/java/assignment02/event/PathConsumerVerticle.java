package assignment02.event;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.List;

public class PathConsumerVerticle extends AbstractVerticle {

    private int count = 0;

    @Override
    public void start() {
        System.out.println(Thread.currentThread().getName() + " PathConsumerVerticle started");
        final List<Future> futures = new ArrayList<>();
        vertx.eventBus().consumer("newPath", message -> {
            System.out.println(Thread.currentThread().getName() + " Pulling newPath from event bus");
            // count file lines
            count++;
            final Future future = vertx.fileSystem().readFile(message.body().toString()).onSuccess(result -> {
                long lines = result.toString().lines().count();
                System.out.println(Thread.currentThread().getName() + " Pushing newStatistic to event bus");
                vertx.eventBus().send("newStatistic", lines);
            });
            futures.add(future);
        });
        vertx.eventBus().consumer("newPath.completed", message -> {
            CompositeFuture.all(futures).onSuccess(x -> {
                System.out.println(Thread.currentThread().getName() + " Pushing newStatistic.completed to event bus");
                vertx.eventBus().send("newStatistic.completed", "completed");
            });
        });
    }

    @Override
    public void stop() {
        System.out.println(Thread.currentThread().getName() + " PathConsumerVerticle stopped (" + count + " files)");
    }
}
