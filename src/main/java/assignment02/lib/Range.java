package assignment02.lib;

import java.util.Objects;

public class Range {
    private final int start;
    private final int end;
    public int base = 10;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Range(int startPow, int endPow, int base) {
        this.start = (int) Math.pow(base, startPow);
        this.end = (int) Math.pow(base, endPow);
        this.base = base;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return start == range.start && end == range.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Range(" + start + ", " + (end == Integer.MAX_VALUE ? "INF" : end) + ")";
    }
}
