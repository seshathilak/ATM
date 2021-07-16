package Atm;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUId() {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        // loop untile we get unique id
        do {
            // generate Number .
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            // check to make sure its unique
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);
        return uuid;
    }

    public String getNewAccountUUID() {
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
        // loop untile we get unique id
        do {
            // generate Number .
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            // check to make sure its unique
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }

    public void addAccount(Account anAccnt) {
        this.accounts.add(anAccnt);
    }

    public User addUser(String firstName, String lastName, String pin) {
        // create a new User Object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        // create a savings Account for the user and add to user and Bank accounts lists
        Account newAcc = new Account("Savings", newUser, this);
        newUser.addAccount(newAcc);
        this.addAccount(newAcc);

        return newUser;

    }

    public User userLogin(String userId, String pin) {
        // search thru list of users
        for (User u : this.users) {
            // chekc user Id is correct
            if (u.getUUID().compareTo(userId) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        // if we havnt found the user or the pin is incorrect pin return null
        return null;
    }

    public String getName() {
        return this.name;

    }
}
