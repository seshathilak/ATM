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
        System.out.printf("\n\n\nNew user %s %s with ID %s created .\n", this.lastName, firstName, this.uuid);

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

    public String getFirstName() {
        return this.firstName;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("   %d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public int numaccounts() {
        return this.accounts.size();
    }

    public void printAccTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    public String getAcctUUID(int accIdx) {
        return this.accounts.get(accIdx).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}