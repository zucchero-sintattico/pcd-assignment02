package assignment02.mvc.model.listeners;


import assignment02.lib.report.Range;

import java.util.Map;
import java.util.function.Consumer;

public interface DistributionChangeListener extends Consumer<Map<Range, Integer>> {
}
