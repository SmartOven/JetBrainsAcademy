package machine.director;

import machine.coffeeMachine.ActivityState;
import machine.builder.CoffeeTypeBuilder;
import machine.coffee.CoffeeType;
import machine.builder.CoffeeMachineBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Director class for CoffeeMachineBuilders
 * Knows the recipes for different coffee machines
 */
public class Director {

    public Director() {}

    /**
     * Creates default coffee machine with 400 ml of water, 540 ml of milk, 120 g of coffee beans, 9 disposable cups, $550 in cash
     * @param builder CoffeeMachineBuilder
     */
    public void constructDefaultCoffeeMachine(CoffeeMachineBuilder builder) {
        builder.setWater(400);
        builder.setMilk(540);
        builder.setBeans(120);
        builder.setDisposableCups(9);
        builder.setMoney(550);
        builder.setCoffeeTypeList(constructDefaultCoffeeMachineCoffeeTypeList());
        builder.setActivityStateStrings(constructDefaultCoffeeMachineActivityStateStrings());
    }

    /**
     * Creates espresso
     * @param builder CoffeeMachineBuilder
     */
    public void constructEspresso(CoffeeTypeBuilder builder) {
        builder.setWater(250);
        builder.setMilk(0);
        builder.setBeans(16);
        builder.setDisposableCups(1);
        builder.setPrice(4);
    }

    /**
     * Creates latte
     * @param builder CoffeeMachineBuilder
     */
    public void constructLatte(CoffeeTypeBuilder builder) {
        builder.setWater(350);
        builder.setMilk(75);
        builder.setBeans(20);
        builder.setDisposableCups(1);
        builder.setPrice(7);
    }

    /**
     * Creates cappuccino
     * @param builder CoffeeMachineBuilder
     */
    public void constructCappuccino(CoffeeTypeBuilder builder) {
        builder.setWater(200);
        builder.setMilk(100);
        builder.setBeans(12);
        builder.setDisposableCups(1);
        builder.setPrice(6);
    }

    /**
     * Creates CoffeeType list for default CoffeeMachine
     * @return List of CoffeeTypes
     */
    private List<CoffeeType> constructDefaultCoffeeMachineCoffeeTypeList() {
        // Constructing coffee types for default coffee machine
        List<CoffeeType> coffeeTypeList = new ArrayList<>();

        CoffeeTypeBuilder espressoBuilder = new CoffeeTypeBuilder();
        constructEspresso(espressoBuilder);

        CoffeeTypeBuilder latteBuilder = new CoffeeTypeBuilder();
        constructLatte(latteBuilder);

        CoffeeTypeBuilder cappuccinoBuilder = new CoffeeTypeBuilder();
        constructCappuccino(cappuccinoBuilder);

        coffeeTypeList.add(espressoBuilder.getResult());
        coffeeTypeList.add(latteBuilder.getResult());
        coffeeTypeList.add(cappuccinoBuilder.getResult());
        return coffeeTypeList;
    }

    private Map<ActivityState, String> constructDefaultCoffeeMachineActivityStateStrings() {
        // Constructing ActivityState strings for default coffee machine
        Map<ActivityState, String> activityStateStrings = new HashMap<>();

        activityStateStrings.put(ActivityState.WAITING_ACTION, "Write action (buy, fill, take, remaining, exit):");
        activityStateStrings.put(ActivityState.INPUT_COFFEE_TYPE, "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        activityStateStrings.put(ActivityState.FILLING_WATER, "Write how many ml of water you want to add:");
        activityStateStrings.put(ActivityState.FILLING_MILK, "Write how many ml of milk  you want to add:");
        activityStateStrings.put(ActivityState.FILLING_COFFEE_BEANS, "Write how many grams of coffee beans you want to add:");
        activityStateStrings.put(ActivityState.FILLING_DISPOSABLE_CUPS, "Write how many disposable cups of coffee you want to add:");
        activityStateStrings.put(ActivityState.STOPPED, null);

        return activityStateStrings;
    }
}
