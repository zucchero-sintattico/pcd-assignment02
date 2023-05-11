package assignment02.event;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathProducerVerticle extends AbstractVerticle {

    final String path;

    public PathProducerVerticle(final String path) {
        this.path = path;
    }

    private Future<Void> scanFolderRecursive(final String path) {
        final Promise<Void> promise = Promise.promise();
        vertx.fileSystem().readDir(path).onSuccess(result -> {
            System.out.println("Scanning " + path);
            final List<Future> futures = result.stream()
                    .map(file -> {
                        final Promise<Void> filePromise = Promise.promise();
                        vertx.fileSystem().lprops(file).onSuccess(fileProps -> {
                            if (fileProps.isDirectory()) {
                                scanFolderRecursive(file).onComplete(x -> {
                                    filePromise.complete();
                                });
                            } else {
                                if (file.endsWith(".java")) {
                                    System.out.println("Pushing file: " + file + " to event bus");
                                    vertx.eventBus().send("newPath", file);
                                }
                                filePromise.complete();
                            }
                        });
                        return filePromise.future();
                    })
                    .collect(Collectors.toList());
            CompositeFuture.join(futures).onSuccess(x -> {
                System.out.println("Completed scanning " + path);
                promise.complete();
            });
        });
        return promise.future();
    }

    @Override
    public void start() {
        scanFolderRecursive(path).onSuccess(x -> {
            System.out.println("Pushing completed to event bus");
            vertx.eventBus().send("newPath.completed", "completed");
        });
    }

    @Override
    public void stop() {
        System.out.println("PathProducerVerticle stopped");
    }
}
