# Simple Banking System
Project from [JetBrains Academy](https://hyperskill.org/) course.  

## About
It is simple banking system with which you can create and manage accounts.  
Project made using Gradle and SQLite database.

## Classes documentation
### Main class
`main(String[] args)`
1) Setting up console for user input.   
2) Creating `Bank` and `BankManager` instances.   
3) Running bank manager work by `work(Scanner console)` method.

### Banking package
Has `db` and `bankAccount` packages, `Bank` and `BankManager` classes

### DB package
#### SQLiteManager class
Abstract class that has `SQLiteDataSource dataSource` field and `void setDataSource(String source)` method.  
Used to create concrete SQLite managers (like `BankAccountSQLiteManager` and `CustomerIDSQLiteManager`)

#### BankAccountSQLiteManager class
Public methods list
1) `void createBankAccountsTableIfNotExists()`
2) `void insertBankAccountIntoTable(BankAccount bankAccount)`
3) `void removeBankAccountFromTable(BankAccount bankAccount)`
4) `void addIncome(BankAccount bankAccount, int amount)`
5) `MoneyTransferStatus transferMoney(BankAccount bankAccount, String cardNumber, int amount)`
6) `BankAccount getBankAccount(String cardNumber, String pinCode)`

#### CustomerIDSQLiteManager class
Public methods list
1) `void createAvailableCustomerIDTableIfNotExists()`
2) `int incrementAndGetNextAvailableCustomerID()`


### Bank account package
Has `builder` package, `BankAccount` class and `BankAccountUtil` class

#### Builder package
Has:
1) `CardNumberBuilder` class that builds `String cardNumber`
2) `Director` class that manages builders work (knows how to use builder to create standard card number)

#### BankAccount class
Has private fields `final String cardNumber`, `final String pinCode` and `int balance`  
Has public setter for `balance` and public getters for every field

#### BankAccountUtil class
Public methods list:
1) `static String generatePinCode()`
2) `static boolean isValidCardNumber(String cardNumber)`
3) `static int calculateCheckSum(String cardNumberWithNoCheckSum)`

### Bank class
It is something like 'director' for his `BankAccountsSQLiteManager accountsManager` and `CustomerIDSQLiteManager customerIDManager`

Public methods list:
1) `BankAccount createNewBankAccount()`
2) `BankAccount loginIntoAccount(String cardNumber, String pinCode)`
3) `void addIncome(BankAccount bankAccount, int amount)`
4) `BankAccountsSQLiteManager.MoneyTransferStatus transferMoney(BankAccount bankAccount, String cardNumber, int amount)`
5) `closeAccount(BankAccount bankAccount)`
6) `void refreshAccountInfo(BankAccount bankAccount)`

### BankManager class
Wrapper for `Bank` class used to work with user trough console
