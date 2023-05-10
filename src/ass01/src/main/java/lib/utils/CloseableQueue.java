package lib.utils;
import lib.queue.Queue;

public interface CloseableQueue<T> extends Queue<T>, Closeable {}
