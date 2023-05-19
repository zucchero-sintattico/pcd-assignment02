package assignment02.reactive;

import assignment02.SourceAnalyzer;
import assignment02.lib.report.ObservableAsyncReport;
import assignment02.lib.report.ReportConfiguration;
import assignment02.lib.report.Statistic;
import assignment02.lib.report.live.ExecutorBasedLiveReport;
import assignment02.lib.report.live.LiveReport;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReactiveSourceAnalyzer implements SourceAnalyzer {
    private final LiveReport liveReport = new ExecutorBasedLiveReport();

    public ReactiveSourceAnalyzer(ReportConfiguration configuration) {
        this.liveReport.setReportConfiguration(configuration);
    }

    @Override
    public ObservableAsyncReport analyzeSources(Path directory) {

        // file walk
        Observable<Path> source = Observable.create(emitter -> {
            log("Start file walk");
            try (final var paths = Files.walk(directory)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".java"))
                        .forEach(e -> {
                            log("Emitting " + e);
                            emitter.onNext(e);

                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
            log("File walk completed");
            emitter.onComplete();
        });

        Observable<Statistic> statsObservable = source
                .subscribeOn(Schedulers.io())
                .map(p -> {
                    final var lines = Files.readAllLines(p);
                    log("Creating statistic for " + p);
                    return new Statistic(p, lines.size());
                });

        statsObservable
                .observeOn(Schedulers.computation())
                .doOnNext(liveReport::addStatistic)
                .doOnComplete(liveReport::complete)
                .subscribe();

        return liveReport;

    }

    @Override
    public void stop() {
        throw new RuntimeException();
    }

    private void log(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
}
