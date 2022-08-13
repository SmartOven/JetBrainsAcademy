package tracker;

import tracker.ui.CLI;

public class Main {

    public static void main(String[] args) {
        CLI userInterface = CLI.getInstance(System.in);
        userInterface.handleCommands();
    }
}
