package assignment.agents;

import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import assignment02.lib.architecture.QueueProducerThread;
import assignment02.lib.synchronization.QueueMonitor;
import assignment02.lib.synchronization.StopMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Scan the given path and enqueue all the java files found.
 * Once the scan is finished, close the queue.
 */
public class PathProducer extends QueueProducerThread<Path> {
    private final Logger logger = LoggerMonitor.getInstance();
    private final StopMonitor stopMonitor;
    private final Path path;
    private final Boolean withSleep;

    public PathProducer(QueueMonitor<Path> queue, Path path, final StopMonitor stopMonitor) {
        this(queue, path, stopMonitor, false);
    }

    public PathProducer(QueueMonitor<Path> queue, Path path, final StopMonitor stopMonitor, final Boolean withSleep) {
        super(queue);
        this.path = path;
        this.stopMonitor = stopMonitor;
        this.withSleep = withSleep;
    }

    private boolean isJavaFile(Path path) {
        return path.toString().endsWith(".java");
    }

    @Override
    public void run() {
        this.logger.log("Starting File Analyzer");
        try (Stream<Path> pathStream = Files.walk(this.path)) {
            pathStream.filter(Files::isRegularFile)
                    .filter(this::isJavaFile)
                    .forEach((f) -> {
                        try {
                            if (this.withSleep) {
                                Thread.sleep(10);
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (this.stopMonitor.hasToBeStopped()) {
                            this.closeQueue();
                            throw new RuntimeException();
                        } else {
                            this.produce(f);
                            this.logger.log("Produced " + f);
                        }
                    });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            // Do nothing
        } finally {
            this.closeQueue();
            this.logger.log("Finished Reading File");
        }
    }
}

