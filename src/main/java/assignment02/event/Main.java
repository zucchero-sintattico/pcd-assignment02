package assignment02.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        System.out.println("Hello World!");
    }

}
