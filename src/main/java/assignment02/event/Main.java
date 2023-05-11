package assignment02.event;

import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) throws Exception {
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
        Thread.sleep(1000);

        vertx.close();


    }

}
