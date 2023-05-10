import assignment.algorithm.AlgorithmConfiguration;
import assignment.algorithm.AssignmentAlgorithm;
import assignment.mvc.model.Model;
import assignment.mvc.model.ModelConfiguration;
import assignment.mvc.model.ModelImpl;
import assignment.mvc.model.Range;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

class AlgorithmTest {

    final Path path;
    final Range pathConsumerRange;
    final int iterations;


    public AlgorithmTest(final Path path, final Range pathConsumerRange, final int iterations) {
        this.path = path;
        this.pathConsumerRange = pathConsumerRange;
        this.iterations = iterations;
    }

    public void run() {

        for (int i = pathConsumerRange.getStart(); i <= pathConsumerRange.getEnd(); i = i * pathConsumerRange.base) {
            final long[] times = new long[iterations];
            for (int j = 0; j < iterations; j++) {
                // Capture the output of standard output
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                // IMPORTANT: Save the old System.out!
                PrintStream old = System.out;
                // Tell Java to use your special stream
                System.setOut(ps);

                final AlgorithmConfiguration configuration = AlgorithmConfiguration.builder()
                        .withNumberOfPathProducer(1)
                        .withNumberOfPathConsumer(i)
                        .withNumberOfStatisticsConsumer(1)
                        .build();
                final Model model = new ModelImpl();
                model.setConfiguration(new ModelConfiguration(10, 5, 1000));
                final AssignmentAlgorithm algorithm = new AssignmentAlgorithm(model, path, configuration);
                final long start = System.currentTimeMillis();
                algorithm.start();
                try {
                    algorithm.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                final long end = System.currentTimeMillis();
                // Restore the standard output
                times[j] = end - start;
                System.out.flush();
                System.setOut(old);

            }
            double average = Arrays.stream(times).average().orElse(-1);
            System.out.println(
                    "Done with " + i + " path consumers in " + average + " ms");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        final Path path = Paths.get("generator");
        final Range pathConsumerRange = new Range(0, 9, 2);
        final int iterations = 10;

        final AlgorithmTest test = new AlgorithmTest(path, pathConsumerRange, iterations);
        test.run();

    }
}

