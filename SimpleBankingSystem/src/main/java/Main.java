import banking.Bank;
import banking.BankManager;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        String databaseUrl;
        if (args.length != 2) {
            // Was used for separate project
            // databaseUrl = new File("src/main/resources/bank_accounts.db").getAbsolutePath();

            // Now used as project module with name "SimpleBankingSystem"
            databaseUrl = new File("SimpleBankingSystem/src/main/resources/bank_accounts.db").getAbsolutePath();
        } else {
            databaseUrl = args[1];
        }

        Bank bank = new Bank(databaseUrl);
        BankManager manager = new BankManager(bank);

        manager.work(console);
    }
}
