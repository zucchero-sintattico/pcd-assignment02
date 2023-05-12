package assignment02.reactive;
import assignment02.lib.report.ReportConfiguration;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
       ReactiveSourceAnalyzer reactiveSourceAnalyzer = new ReactiveSourceAnalyzer(new ReportConfiguration(3, 10, 20));
       reactiveSourceAnalyzer.analyzeSources(Path.of("src"));
    }
}
