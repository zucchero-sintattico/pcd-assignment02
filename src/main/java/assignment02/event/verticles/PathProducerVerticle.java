package assignment02.event.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;

import java.util.List;

@SuppressWarnings("rawtypes")
public class PathProducerVerticle extends AbstractVerticle {

    final String path;
    private int count = 0;

    public PathProducerVerticle(final String path) {
        this.path = path;
    }

    private Future scanElement(final String path) {
        final Promise<Void> promise = Promise.promise();
        vertx.fileSystem().lprops(path).onSuccess(fileProps -> {
            if (fileProps.isDirectory()) {
                scanFolderRecursive(path).onComplete(x -> {
                    promise.complete();
                });
            } else {
                if (path.endsWith(".java")) {
                    count++;
                    log("Pushing new path: '" + path + "' to event bus");
                    vertx.eventBus().send("newPath", path);
                }
                promise.complete();
            }
        });
        return promise.future();
    }

    private Future<Void> scanFolderRecursive(final String path) {
        final Promise<Void> promise = Promise.promise();
        vertx.fileSystem().readDir(path).onSuccess(result -> {
            log("Scanning " + path);
            final List<Future> futures = result.stream()
                    .map(this::scanElement)
                    .toList();
            CompositeFuture.all(futures).onSuccess(x -> {
                log("Completed scanning " + path);
                promise.complete();
            });
        });
        return promise.future();
    }

    @Override
    public void start() {
        log("PathProducerVerticle started");
        scanFolderRecursive(path).onSuccess(x -> {
            log("PathProducerVerticle completed, found " + count + " files");
            log("PathProducerVerticle sending newPath.completed message");
            vertx.eventBus().send("pathSearch.completed", "completed");
        });
    }

    @Override
    public void stop() {
        log("PathProducerVerticle stopped");
    }

    private void log(final String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] : " + message);
    }
}
