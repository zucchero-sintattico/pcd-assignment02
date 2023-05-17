package assignment02.mvc.model.listeners;


import assignment02.lib.report.Statistic;

import java.util.List;
import java.util.function.Consumer;

public interface TopChangeListener extends Consumer<List<Statistic>> {
}
