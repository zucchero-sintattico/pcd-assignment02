package assignment02.event;

import io.vertx.core.AbstractVerticle;

public class PathConsumerVerticle extends AbstractVerticle {

    @Override
    public void start() {
        System.out.println("PathConsumerVerticle started");
    }

    @Override
    public void stop() {
        System.out.println("PathConsumerVerticle stopped");
    }
}
