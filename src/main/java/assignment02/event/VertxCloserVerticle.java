package assignment02.event;

import io.vertx.core.AbstractVerticle;

public class VertxCloserVerticle extends AbstractVerticle {


    @Override
    public void start() {
        log("VertxCloserVerticle started");
        vertx.eventBus().consumer("completed", message -> {
            log("VertxCloserVerticle received completed message");
            log("VertxCloserVerticle closing vertx");
            vertx.close();
        });
    }

    @Override
    public void stop() {
        log("VertxCloserVerticle stopped");
    }

    private void log(final String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] : " + message);
    }
}
