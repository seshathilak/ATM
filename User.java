package Atm;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String firstName;

    private String lastName;

    private String uuid;

    private byte pinHash[];

    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        // MD5 hashing
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());

        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NOsuchAlgException");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new uuid

        this.uuid = theBank.getNewUserUUId();

        // create empty list of accounts
        this.accounts = new ArrayList<Account>();

        // log Message
        System.out.printf("New user %S %s with ID %s created .\n", lastName, firstName, this.uuid);

    }

    public void addAccount(Account anAccnt) {
        this.accounts.add(anAccnt);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NOsuchAlgException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
}