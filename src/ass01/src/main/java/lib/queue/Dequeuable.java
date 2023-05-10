package assignment02.lib.queue;

import java.util.Optional;

public interface Dequeuable<T> {
    Optional<T> dequeue();
}
