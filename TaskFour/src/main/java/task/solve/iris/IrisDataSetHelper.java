package task.solve.iris;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class IrisDataSetHelper {

    private List<Iris> dataSet;

    public IrisDataSetHelper(List<Iris> dataSet) { this.dataSet = dataSet; }

    public Double getAverage(ToDoubleFunction<Iris> func) {
        return this.dataSet
                .stream()
                .mapToDouble(func)
                .average()
                .getAsDouble();
    }

    public List<Iris> filter(Predicate<Iris> predicate) {
        return this.dataSet
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Double getAverageWithFilter(Predicate<Iris> filter, ToDoubleFunction<Iris> mapFunction) {
        return this.dataSet
                .stream()
                .filter(filter)
                .mapToDouble(mapFunction)
                .average()
                .getAsDouble();
    }

    public <T> Map<T, List<Iris>> groupBy(Function<Iris, T> groupFunction) {
        return this.dataSet
                .stream()
                .collect(Collectors.groupingBy(groupFunction));
    }

    public <T> Map<T, Optional<Iris>> maxFromGroupedBy(Function<Iris, T> groupFunction, ToDoubleFunction<Iris> obtainMaximisationValueFunction) {
        return this.dataSet
                .stream()
                .collect(
                        Collectors.groupingBy(
                                groupFunction,
                                Collectors.maxBy(Comparator.comparingDouble(obtainMaximisationValueFunction))
                ));
    }
}
