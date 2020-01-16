public class Item {
    private String name;
    private double weight;

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    //public void setName(String n) {name = n;}
    public String getName() {
        return name;
    }
    //public void setWeight(double w) { weight = w; }
    public double getWeight() {
        return weight;
    }

}
