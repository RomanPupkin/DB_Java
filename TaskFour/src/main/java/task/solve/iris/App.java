package task.solve.iris;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class App {
    public static void main(String[] args) throws IOException {
        App a = new App();
        a.test();
    }

    public void test() throws IOException {

        //load data from file iris.data
        List<Iris> irisList = Files
                .lines(Paths.get("iris.data"))
                .map(Iris::parse)
                .collect(Collectors.toList());
        IrisDataSetHelper helper = new IrisDataSetHelper(irisList);

        //get average sepal width
        Double avgSepalLength = helper.getAverage(Iris::getSepalWidth);
        System.out.println(avgSepalLength);

        //get average petal square - petal width multiplied on petal length
        Double avgPetalSq = helper.getAverage(e -> e.getPetalLength() * e.getPetalWidth());
        System.out.println(avgPetalSq);

        //get average petal square for flowers with sepal width > 4
        Double avgPetalSquare = helper.getAverageWithFilter(e -> e.getSepalWidth() > 4, e -> e.getPetalLength() * e.getPetalWidth());
        System.out.println(avgPetalSquare);

        //get flowers grouped by Petal size (Petal.SMALL, etc.)
        Map groupsByPetalSize = helper.groupBy(e -> Iris.classifyByPatel((Iris) e));
        System.out.println(groupsByPetalSize);

        //get max sepal width for flowers grouped by species
        Map maxSepalWidthForGroupsBySpecies = helper.maxFromGroupedBy(Iris::getSpecies, Iris::getSepalWidth);
        System.out.println(maxSepalWidthForGroupsBySpecies);
    }

}

