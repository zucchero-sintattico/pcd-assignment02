package assignment02;

import assignment02.event.EventBasedSourceAnalyser;
import assignment02.executor.TaskBasedSourceAnalyzer;
import assignment02.lib.report.Report;
import assignment02.lib.report.ReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.concurrent.Future;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        long time = test.RunEventBasedSourceAnalyser();
        System.out.println("EventBasedSourceAnalyser: " + time + "ms");
        time = test.RunExecutorBasedSourceAnalyzer();
        System.out.println("ExecutorBasedSourceAnalyzer: " + time + "ms");
    }

    long RunEventBasedSourceAnalyser() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(outputStream);
        final PrintStream old = System.out;
        System.setOut(ps);
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyzer sourceAnalyser = new EventBasedSourceAnalyser(configuration);
        final Future<Report> report = sourceAnalyser.getReport(Path.of("src"));
        final long tic = System.currentTimeMillis();
        try {
            report.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final long toc = System.currentTimeMillis();
        System.out.flush();
        System.setOut(old);
        return toc - tic;
    }

    long RunExecutorBasedSourceAnalyzer() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(outputStream);
        final PrintStream old = System.out;
        System.setOut(ps);
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyzer sourceAnalyser = new TaskBasedSourceAnalyzer(configuration);
        final Future<Report> report = sourceAnalyser.getReport(Path.of("src"));
        long tic = System.currentTimeMillis();
        try {
            report.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long toc = System.currentTimeMillis();
        System.out.flush();
        System.setOut(old);
        return toc - tic;
    }


}

