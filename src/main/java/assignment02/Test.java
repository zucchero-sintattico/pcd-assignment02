package assignment02;

import assignment02.event.EventBasedSourceAnalyser;
import assignment02.executor.TaskBasedSourceAnalyzer;
import assignment02.lib.report.Report;
import assignment02.lib.report.ReportConfiguration;
import assignment02.reactive.ReactiveSourceAnalyzer;
import assignment02.virtual.VirtualThreadBasedSourceAnalyzer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class Test {
    public static void main(String[] args) {
        List<Integer> nFiles = List.of(33, 100,1000, 10000, 30000);
        System.out.println("Event - Task - Reactive - Virtual");
        nFiles.forEach( n -> {
            try {
                oneStepPerSourceAnalyser(n, 5);
            }
            catch (Exception e) {
            }

        });
    }


    static PrintStream switchOutputStream() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(outputStream);
        final PrintStream old = System.out;
        System.setOut(ps);
        return old;
    }

    static void resetOuputStream(PrintStream old) {
        System.out.flush();
        System.setOut(old);
    }

    private static long oneStepPerSourceAnalyser(int path, int nMeasures) {

        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        long partialResult;

        List<SourceAnalyzer> sourceAnalyzers = List.of(new EventBasedSourceAnalyser(configuration),
                new TaskBasedSourceAnalyzer(configuration),
                new ReactiveSourceAnalyzer(configuration),
                new VirtualThreadBasedSourceAnalyzer(configuration));
        List<Integer> results = new ArrayList<>();
        for (SourceAnalyzer sourceAnalyser : sourceAnalyzers) {
            PrintStream old = switchOutputStream();
            Future<Report> report = sourceAnalyser.getReport(Path.of("src/generator/"+(path)));
            partialResult = 0;
            for (int j = 1; j <= nMeasures; j++) {

                long tic = System.currentTimeMillis();
                try {
                    report.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long toc = System.currentTimeMillis();
                partialResult = (toc - tic) + partialResult;

            }
            results.add((int) (partialResult / nMeasures));
            resetOuputStream(old);

        }
        System.out.println("[ "+path+" ] - " + results);
    return 0;
    }
}
