package machine.coffeeMachine;

import machine.coffee.CoffeeType;

import java.util.List;
import java.util.Map;

public class CoffeeMachine {

    private ActivityState activityState;
    private int waterAmount;
    private int milkAmount;
    private int beansAmount;
    private int disposableCupsAmount;
    private int money;
    private final List<CoffeeType> coffeeTypeList;
    private final Map<ActivityState, String> activityStateStrings;

    public CoffeeMachine(int waterAmount, int milkAmount, int beansAmount, int disposableCupsAmount, int money, List<CoffeeType> coffeeTypeList, Map<ActivityState, String> activityStateStrings) {
        this.activityState = ActivityState.WAITING_ACTION;
        this.waterAmount = waterAmount;
        this.milkAmount = milkAmount;
        this.beansAmount = beansAmount;
        this.disposableCupsAmount = disposableCupsAmount;
        this.money = money;
        this.coffeeTypeList = coffeeTypeList;
        this.activityStateStrings = activityStateStrings;
    }

    /**
     *
     * @return true if machine is stopped; false if not
     */
    public boolean isStopped() {
        return activityState.equals(ActivityState.STOPPED);
    }

    /**
     * Outputs in console pre-input string (if not null) with info about what should user input
     */
    public void makePreInputActivity() {
        String preInputOutput = activityStateStrings.get(activityState);
        if (preInputOutput != null) {
            System.out.println(preInputOutput);
        }
    }

    /**
     * User input handler using activity states
     * @param input String with user input
     */
    public void handleStringInput(String input) {
        switch (activityState) {
            case WAITING_ACTION ->
                    // Getting operation type
                    waitingActionResolver(input);
            case INPUT_COFFEE_TYPE ->
                    // Getting coffee type
                    inputCoffeeTypeResolver(input);
            case FILLING_WATER ->
                    // Getting amount of water to fill
                    fillingWaterResolver(input);
            case FILLING_MILK ->
                    // Getting amount of milk to fill
                    fillingMilkResolver(input);
            case FILLING_COFFEE_BEANS ->
                    // Getting amount of coffee beans to fill
                    fillingCoffeeBeansResolver(input);
            case FILLING_DISPOSABLE_CUPS ->
                    // Getting amount of disposable cups to fill
                    fillingDisposableCupsResolver(input);
        }
    }

    /**
     * Resolves action type input
     * @param input String with user input
     */
    private void waitingActionResolver(String input) {
        switch (input) {
            case "buy" ->
                    // User wants to buy a coffee
                    // Next to do is to get coffee type from user's input
                    activityState = ActivityState.INPUT_COFFEE_TYPE;
            case "fill" ->
                    // Worker wants to fill coffee machine resources
                    // Next to do is to go to the asking resources states
                    // First one is FILLING_WATER
                    activityState = ActivityState.FILLING_WATER;
            case "take" -> {
                // Another worker wants to take money from coffee machine
                // Next to do is to give him the money
                activityState = ActivityState.WAITING_ACTION;
                // Taking money from machine
                takeMoneyFromMachine();
            }
            case "remaining" -> {
                // Someone wants to check how many resources left in coffee machine
                // Next to do is to tell him the info
                activityState = ActivityState.WAITING_ACTION;
                // Printing resources state
                printResourcesState();
            }
            case "exit" ->
                    // Stopping machine
                    activityState = ActivityState.STOPPED;
            default ->
                    // Got wrong option
                    // Activity state doesn't change
                    System.out.println("Not an option! Try again...");
        }
    }

    /**
     * User input is the ID of coffee type that he wants to make
     * What we do is:
     *  1) Get coffee type that he wants by ID
     *  2) Check if we can make the coffee for the user
     *  3) If unable, tell the user that there is not enough resources for that coffee type
     *  4) If able, make the coffee for the user:
     *      3.1) Subtract the ingredients that coffee type requires from the coffee machine resources
     *      3.2) Add coffee price to the coffee machine money storage
     *      3.3) Tell user that everything went well
     * @param input String with user input
     */
    private void inputCoffeeTypeResolver(String input) {
        activityState = ActivityState.WAITING_ACTION;
        // If user changed mind
        if (input.equals("back")) {
            return;
        }

        // Resolving coffee type from the input
        int coffeeTypeIndex = Integer.parseInt(input) - 1;
        CoffeeType coffeeType = coffeeTypeList.get(coffeeTypeIndex);

        // Checking if there is enough water
        if (!(waterAmount >= coffeeType.water)) {
            System.out.println("Sorry, not enough water!");
            return;
        }

        // Checking if there is enough milk
        if (!(milkAmount >= coffeeType.milk)) {
            System.out.println("Sorry, not enough milk!");
            return;
        }

        // Checking if there is enough beans
        if (!(beansAmount >= coffeeType.beans)) {
            System.out.println("Sorry, not enough beans!");
            return;
        }

        // Checking if there is enough disposable cups
        if (!(disposableCupsAmount >= coffeeType.disposableCups)) {
            System.out.println("Sorry, not enough disposable cups!");
            return;
        }

        // Telling user there is enough resources to make him coffee
        System.out.println("I have enough resources, making you a coffee!");

        // Making him coffee
        waterAmount -= coffeeType.water;
        milkAmount -= coffeeType.milk;
        beansAmount -= coffeeType.beans;
        disposableCupsAmount -= 1;
        money += coffeeType.price;
    }

    /**
     * Filling water
     * @param input String with user input
     */
    private void fillingWaterResolver(String input) {
        activityState = ActivityState.FILLING_MILK;
        waterAmount += Integer.parseInt(input);
    }

    /**
     * Filling milk
     * @param input String with user input
     */
    private void fillingMilkResolver(String input) {
        activityState = ActivityState.FILLING_COFFEE_BEANS;
        milkAmount += Integer.parseInt(input);
    }

    /**
     * Filling coffee beans
     * @param input String with user input
     */
    private void fillingCoffeeBeansResolver(String input) {
        activityState = ActivityState.FILLING_DISPOSABLE_CUPS;
        beansAmount += Integer.parseInt(input);
    }

    /**
     * Filling disposable cups
     * @param input String with user input
     */
    private void fillingDisposableCupsResolver(String input) {
        activityState = ActivityState.WAITING_ACTION;
        disposableCupsAmount += Integer.parseInt(input);
    }

    /**
     * Prints machine resources state
     */
    private void printResourcesState() {
        System.out.println(getResourcesState());
    }

    /**
     * Taking money from the machine
     */
    private void takeMoneyFromMachine() {
        activityState = ActivityState.WAITING_ACTION;
        System.out.println("I gave you $" + money);
        money = 0;
    }

    /**
     * @return State of CoffeeMachine as String
     */
    private String getResourcesState() {
        return "The coffee machine has:\n" +
                waterAmount + " ml of water\n" +
                milkAmount + " ml of milk\n" +
                beansAmount + " g of coffee beans\n" +
                disposableCupsAmount + " disposable cups\n" +
                "$" + money + " of money";
    }
}
