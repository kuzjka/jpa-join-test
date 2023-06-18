package ua.kuzjka.jpatest.generator;

public class SelectionWeight {
    private int itemCount;
    private double weight;

    /**
     * Creates new generation weights.
     * @param itemCount     Number of items for this weight
     * @param weight        Weight value
     */
    public SelectionWeight(int itemCount, double weight) {
        this.itemCount = itemCount;
        this.weight = weight;
    }

    /**
     * Gets number of items for this weight.
     * @return  Number of items
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Gets generation weight.
     * @return  Weight value.
     */
    public double getWeight() {
        return weight;
    }
}
