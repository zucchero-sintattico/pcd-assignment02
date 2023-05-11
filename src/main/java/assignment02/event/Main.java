package assignment02.event;

import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        EventBasedSourceAnalyser eventBasedSourceAnalyser = new EventBasedSourceAnalyser();
        eventBasedSourceAnalyser.analyzeSources(Path.of("."));
    }

}
