package assignment02.event;

import io.vertx.core.AbstractVerticle;

public class PathProducerVerticle extends AbstractVerticle {

    @Override
    public void start() {
        System.out.println("PathProducerVerticle started");
    }

    @Override
    public void stop() {
        System.out.println("PathProducerVerticle stopped");
    }
}
