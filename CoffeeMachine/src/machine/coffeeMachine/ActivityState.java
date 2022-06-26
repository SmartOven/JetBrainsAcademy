package machine.coffeeMachine;

public enum ActivityState {
    // Choosing type of activity: buy, fill, take, remaining, exit
    WAITING_ACTION,

    // Buying coffee
    INPUT_COFFEE_TYPE,

    // Filling resources
    FILLING_WATER,
    FILLING_MILK,
    FILLING_COFFEE_BEANS,
    FILLING_DISPOSABLE_CUPS,

    // Finish state
    STOPPED
}