package machine.builder;

import machine.coffeeMachine.ActivityState;
import machine.coffeeMachine.CoffeeMachine;
import machine.coffee.CoffeeType;

import java.util.List;
import java.util.Map;

/**
 * Builder for CoffeeMachine
 * builds coffee machines step-by-step
 */
public class CoffeeMachineBuilder implements Builder {
    private int waterAmount;
    private int milkAmount;
    private int beansAmount;
    private int disposableCupsAmount;
    private int money;
    private List<CoffeeType> coffeeTypeList;
    private Map<ActivityState, String> activityStateStrings;

    public CoffeeMachineBuilder() {}

    @Override
    public void setWater(int water) {
        this.waterAmount = water;
    }

    @Override
    public void setMilk(int milk) {
        this.milkAmount = milk;
    }

    @Override
    public void setBeans(int beans) {
        this.beansAmount = beans;
    }

    @Override
    public void setDisposableCups(int disposableCups) {
        this.disposableCupsAmount = disposableCups;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setCoffeeTypeList(List<CoffeeType> coffeeTypeList) {
        this.coffeeTypeList = coffeeTypeList;
    }

    public void setActivityStateStrings(Map<ActivityState, String> activityStateStrings) {
        this.activityStateStrings = activityStateStrings;
    }

    /**
     * @return Built object of class CoffeeMachine
     */
    public CoffeeMachine getResult() {
        return new CoffeeMachine(waterAmount, milkAmount, beansAmount, disposableCupsAmount, money, coffeeTypeList, activityStateStrings);
    }
}
