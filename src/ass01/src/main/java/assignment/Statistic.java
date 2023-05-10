package assignment;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Class for storing information about file and number of lines in it.
 */
public final class Statistic{
    public final Path file;
    public final int linesCount;

    public Statistic(final Path file, final int linesCount) {
        this.linesCount = linesCount;
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return linesCount == statistic.linesCount && Objects.equals(file, statistic.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, linesCount);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "file=" + file +
                ", linesCount=" + linesCount +
                '}';
    }
}

