package assignment02.event;

import assignment02.Statistic;
import io.vertx.core.AbstractVerticle;

import java.nio.file.Path;

public class PathConsumerVerticle extends AbstractVerticle {


    @Override
    public void start() {
        vertx.eventBus().consumer("newPath", message -> {
            // count file lines
            vertx.fileSystem().readFile(message.body().toString()).onComplete(res -> {
                int length = res.result().toString().split("\n").length;
                System.out.println("Pushing statistic of " + message.body() + " to event bus");
                vertx.eventBus().send("newStatistic", length);
            });
        });
        vertx.eventBus().consumer("newPath.completed", message -> {
            System.out.println("Pushing completed to event bus");
            vertx.eventBus().send("newStatistic.completed", "completed");
        });
    }

    @Override
    public void stop() {
        System.out.println("PathConsumerVerticle stopped");
    }
}
