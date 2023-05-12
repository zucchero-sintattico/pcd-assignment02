package assignment02.reactive;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
       ReactiveSourceAnalyzer reactiveSourceAnalyzer = new ReactiveSourceAnalyzer();
       reactiveSourceAnalyzer.analyzeSources(Path.of("src"));
    }
}
