package assignment02.event;

import io.vertx.core.Promise;
import io.vertx.core.Verticle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        Verticle pathProducerVerticle = new PathProducerVerticle();
        Verticle pathConsumerVerticle = new PathConsumerVerticle();
    }

}
