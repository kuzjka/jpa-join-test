package ua.kuzjka.jpatest.generator;

import java.util.*;

public class WeightedSelector<T> {

    private Random random = new Random();
    private NavigableMap<Double, T> selectionMap = new TreeMap<>();


    /**
     * Creates new selector. Selector strategy defines how many items from the list could be selected and
     * the probability of selection.
     *
     * For example, given a list of 10 items and weights {count: 3, weight: 3}, {count: 1, weight: 1},
     * selector will randomly choose 4 items of the list and will return 3 of them with probability 30%
     * and one of them with probability 10%. Other 6 items will never be selected.
     * @param items     items to select from
     * @param weights   weights
     */
    public WeightedSelector(List<T> items, List<SelectionWeight> weights) {
        int totalCount = weights.stream().mapToInt(SelectionWeight::getItemCount).sum();
        if (totalCount > items.size())
            throw new IllegalArgumentException("Total number of weighted items should not exceed total number of items");

        List<T> shuffled = new ArrayList<>(items);
        Collections.shuffle(shuffled);

        double total = weights.stream().mapToDouble(w -> w.getWeight() * w.getItemCount()).sum();
        double factor = 1 / total;
        int currentItem = 0;
        double currentKey = 0;

        for (SelectionWeight w : weights) {
            for (int i = 0; i <w.getItemCount(); ++i) {
                currentKey += w.getWeight() * factor;
                selectionMap.put(currentKey, items.get(currentItem++));
            }
        }
    }

    /**
     * Selects next item.
     * @return  Randomly selected item according to selection strategy.
     */
    public T selectItem() {
        double dice = random.nextDouble();
        return selectionMap.ceilingEntry(dice).getValue();
    }
}
