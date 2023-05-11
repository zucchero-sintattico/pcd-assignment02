package assignment02.event;

import assignment02.Statistic;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

import java.nio.file.Path;
import java.util.List;

public class PathConsumerVerticle extends AbstractVerticle {

    private int count = 0;

    @Override
    public void start() {
        System.out.println(Thread.currentThread().getName() + " PathConsumerVerticle started");
        vertx.eventBus().consumer("newPath", message -> {
            System.out.println(Thread.currentThread().getName() + " Pulling newPath from event bus");
            // count file lines
            count++;
            long lines = vertx.fileSystem().readFileBlocking(message.body().toString()).toString().lines().count();
            System.out.println(Thread.currentThread().getName() + " Pushing newStatistic to event bus");
            vertx.eventBus().send("newStatistic", lines);
        });
        vertx.eventBus().consumer("newPath.completed", message -> {
            System.out.println(Thread.currentThread().getName() + " Pushing newStatistic.completed to event bus");
            vertx.eventBus().send("newStatistic.completed", "completed");
        });
    }

    @Override
    public void stop() {
        System.out.println(Thread.currentThread().getName() + " PathConsumerVerticle stopped (" + count + " files)");
    }
}
