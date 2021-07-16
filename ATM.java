package Atm;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Bank of India");

        // Add a user which also creates a savings account
        User aUser = theBank.addUser("Seshathilak", "G", "test123");

        // Adding a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            // Stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);

            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;

        // prompt the usr for user id and pw until correct one is reached

        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user Id: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID or pin " + "Please try again ");

            }

        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {

        // print a summary of user's accounts
        theUser.printAccountsSummary();

        int choice;

        // user menu
        do {
            System.out.printf("Welcome %s,what would like to do ?\n", theUser.getFirstName());
            System.out.println("   1) Show account transaction history");
            System.out.println("   2) Withdrawl");
            System.out.println("   3) Deposti");
            System.out.println("   4) Transfer");
            System.out.println("   5) Quit");
            System.out.println();
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice . Please Choose 1-5");

            }
            ;
        } while (choice < 1 || choice > 5);

        // process the choice
        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;

            case 2:
                ATM.withdrawlFunds(theUser, sc);
                break;

            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;

        }
        // redisplay this menu unless the user wants to quit

        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }

    }

    public static void showTransHistory(User theUser, Scanner sc) {
        int theAcct;

        // get account whose transaction history to look upon
        do {
            System.out.printf("Enter the Number (1-%d) of the account whose transactions you want to see : ",
                    theUser.numaccounts());

            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numaccounts()) {
                System.out.println("Invalid Account , Please try agian .");
            }
        } while (theAcct < 0 || theAcct >= theUser.numaccounts());

        // print the transaction history
        theUser.printAccTransHistory(theAcct);
    }

    public static void transferFunds(User theUser, Scanner sc) {
        // inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer from: ", theUser.numaccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numaccounts()) {
                System.out.println("Invalid Account , Please try agian .");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numaccounts());

        acctBal = theUser.getAcctBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer to: ", theUser.numaccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numaccounts()) {
                System.out.println("Invalid Account , Please try agian .");
            }
        } while (toAcct < 0 || toAcct >= theUser.numaccounts());

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max Rs%.02f): Rs ", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("AMount must not be greater \n balance of Rs%.02f.\n", acctBal);

            }
        } while (amount < 0 || amount > acctBal);

        // finally, do the transfer !!
        theUser.addAcctTransaction(fromAcct, -1 * amount,
                String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount,
                String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));

    }

    public static void withdrawlFunds(User theUser, Scanner sc) {
        // inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;
        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", theUser.numaccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numaccounts()) {
                System.out.println("Invalid Account , Please try agian .");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numaccounts());

        acctBal = theUser.getAcctBalance(fromAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to withdraw (max Rs%.02f): Rs ", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("AMount must not be greater \n balance of Rs%.02f.\n", acctBal);

            }
        } while (amount < 0 || amount > acctBal);

        // gobble up rest of previous input
        sc.nextLine();

        // get a memo
        System.out.print("Enter a memo :");
        memo = sc.nextLine();

        // do the Withdrawl
        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
    }

    public static void depositFunds(User theUser, Scanner sc) {
        // inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;
        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to deposit in: ", theUser.numaccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numaccounts()) {
                System.out.println("Invalid Account , Please try agian .");
            }
        } while (toAcct < 0 || toAcct >= theUser.numaccounts());

        acctBal = theUser.getAcctBalance(toAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to deposit Rs: ");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);

        // gobble up rest of previous input
        sc.nextLine();

        // get a memo
        System.out.print("Enter a memo :");
        memo = sc.nextLine();

        // do the Deposit
        theUser.addAcctTransaction(toAcct, amount, memo);
    }

}
