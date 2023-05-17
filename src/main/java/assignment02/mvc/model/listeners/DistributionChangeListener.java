package assignment02.mvc.model.listeners;

import assignment.mvc.model.Range;
import assignment02.lib.architecture.Consumer;

import java.util.Map;

public interface DistributionChangeListener extends Consumer<Map<Range, Integer>> {
}
