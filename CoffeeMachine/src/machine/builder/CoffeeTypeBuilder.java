package machine.builder;

import machine.coffee.CoffeeType;

public class CoffeeTypeBuilder implements Builder {
    private int water;
    private int milk;
    private int beans;
    private int disposableCups;
    private int price;

    @Override
    public void setWater(int water) {
        this.water = water;
    }

    @Override
    public void setMilk(int milk) {
        this.milk = milk;
    }

    @Override
    public void setBeans(int beans) {
        this.beans = beans;
    }

    @Override
    public void setDisposableCups(int disposableCups) {
        this.disposableCups = disposableCups;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public CoffeeType getResult() {
        return new CoffeeType(water, milk, beans, disposableCups, price);
    }
}
