package assignment02.event;

import io.vertx.core.AbstractVerticle;

public class VertxCloserVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.eventBus().consumer("completed", message -> {
            System.out.println("Closing Vertx");
            vertx.close();
        });
    }

    @Override
    public void stop() {
        System.out.println("VertxCloserVerticle stopped");
    }
}
