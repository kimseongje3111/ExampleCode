package Phase_08.Contents_03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheeseExample {
    private static final List<Cheese> cheeseInStock = new ArrayList<>();

    public static List<Cheese> getCheeses() {
        return cheeseInStock.isEmpty() ? null : new ArrayList<>(cheeseInStock);
    }

    public static List<Cheese> getCheeses2() {
        return cheeseInStock.isEmpty() ? Collections.emptyList() : new ArrayList<>(cheeseInStock);
    }
}

class Cheese {
    static final Cheese STILTON = new Cheese();
}