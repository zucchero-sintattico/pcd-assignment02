package assignment02.lib.utils;

import assignment02.lib.queue.Queue;

public interface CloseableQueue<T> extends Queue<T>, Closeable {
}
