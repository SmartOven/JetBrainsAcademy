package machine.coffee;

public class CoffeeType {
    public int water; // ml
    public int milk;  // ml
    public int beans; // g
    public int disposableCups;
    public int price; // $

    public CoffeeType(int water, int milk, int beans, int disposableCups, int price) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.disposableCups = disposableCups;
        this.price = price;
    }
}
