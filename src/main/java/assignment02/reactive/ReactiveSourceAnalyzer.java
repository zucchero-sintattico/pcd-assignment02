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
            Files.walkFileTree(directory, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs) throws IOException {
                    if(file.toString().endsWith(".java")){
                        System.out.println("Found file: " + file);
                        emitter.onNext(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            emitter.onComplete();
        });
        Observable<Statistic> statsObservable = Observable.create(emitter -> {
            source.subscribe(p -> {
                try {
                    System.out.println(p + " : " + Files.readAllLines(p).size());
                    emitter.onNext(new Statistic(p, Files.readAllLines(p).size()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            emitter.onComplete();
        });
        statsObservable.subscribe(
                liveReport::addStatistic,
                Throwable::printStackTrace,
                () -> {
                    liveReport.complete();
                    System.out.println("Finish, LiveReport completed");
                }
        );
        return liveReport;

    }
}
