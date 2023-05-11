package assignment02;

import java.nio.file.Path;

public class GenericMain {
    public static void execute(final SourceAnalyser sourceAnalyser) {
        sourceAnalyser.getReport(Path.of(".")).thenAccept(System.out::println);
    }
}
