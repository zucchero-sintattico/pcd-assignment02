package assignment02.virtual;

import assignment02.SourceAnalyser;
import assignment02.lib.ReportConfiguration;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyser sourceAnalyser = new VirtualThreadBasedSourceAnalyser(configuration);
        sourceAnalyser.getReport(Path.of(".")).thenAccept(System.out::println);
    }
}
