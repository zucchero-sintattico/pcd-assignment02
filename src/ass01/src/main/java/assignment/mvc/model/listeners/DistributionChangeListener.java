package assignment.mvc.model.listeners;

import assignment.mvc.model.Range;
import lib.architecture.Consumer;

import java.util.Map;

public interface DistributionChangeListener extends Consumer<Map<Range, Integer>> {
}
