package machine;

import machine.builder.CoffeeMachineBuilder;
import machine.coffeeMachine.CoffeeMachine;
import machine.director.Director;

import java.util.Scanner;

/**
 * Solution for CoffeeMachine project from hyperskill
 * @author Yaroslav Panteleev (https://github.com/SmartOven)
 */
public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        // Building default coffee machine
        Director director = new Director();
        CoffeeMachineBuilder defaultCoffeeMachineBuilder = new CoffeeMachineBuilder();
        director.constructDefaultCoffeeMachine(defaultCoffeeMachineBuilder);

        // Getting the result of building default coffee machine
        CoffeeMachine coffeeMachine = defaultCoffeeMachineBuilder.getResult();

        String input;
        while (!coffeeMachine.isStopped()) {
            coffeeMachine.makePreInputActivity();
            input = console.nextLine();
            coffeeMachine.handleStringInput(input);
        }
    }
}
