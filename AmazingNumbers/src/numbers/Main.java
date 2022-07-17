package numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        Manager manipulator = new Manager(console);
        manipulator.work();
    }
}

