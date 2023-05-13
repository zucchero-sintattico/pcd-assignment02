package assignment02.reactive;

import assignment02.SourceAnalyzer;
import assignment02.lib.report.ObservableAsyncReport;
import assignment02.lib.report.ReportConfiguration;
import assignment02.lib.report.Statistic;
import assignment02.lib.report.live.LiveReport;
import io.reactivex.rxjava3.core.Observable;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

public class ReactiveSourceAnalyzer implements SourceAnalyzer {
    private final LiveReport liveReport = new LiveReport();

    public ReactiveSourceAnalyzer(ReportConfiguration configuration) {
        this.liveReport.setReportConfiguration(configuration);
    }

    @Override
    public ObservableAsyncReport analyzeSources(Path directory) {
        // file walk
        Observable<Path> source = Observable.create(emitter -> {
            Files.walk(directory)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(p -> {
                        System.out.println("Found file: " + p);
                        emitter.onNext(p);
                    });
            emitter.onComplete();
        });
        Observable<Statistic> statsObservable = source.map(p -> {
            try {
                System.out.println("Counting " + p + ": " + Files.readAllLines(p).size());
                return new Statistic(p, Files.readAllLines(p).size());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
        statsObservable.subscribe(
                (s) -> {
                    liveReport.addStatistic(s);
                    System.out.println("Statistic added: " + s);
                },
                Throwable::printStackTrace,
                () -> {
                    liveReport.complete();
                    System.out.println("Finish, LiveReport completed");
                }
        );

        return liveReport;

    }
}
