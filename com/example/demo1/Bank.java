package com.example.demo1;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Bank implements BankFunctions ,Serializable{
    private AccountHolder[] accountHolder = new AccountHolder[1000];
    private ArrayList<BankAccount> account = new ArrayList<>();

    int c = 0;

    public ArrayList<BankAccount> getAccounts(){
        return account;
    }
    public void createAccount(BankAccount acc, AccountHolder accountHolders) {
        account.add(acc);
        accountHolder[c] = accountHolders;
        c++;
        acc.setAccountNumber(c);
        System.out.println("Your account Detail: " + acc.toString());
        System.out.println("\nYour User Detail: " + accountHolders.toString());

        return;
    }

    public void depositCash(int accountNumber, double amount) {
        for (int i = 0; i < account.size(); i++) {
            if (account.get(i) != null && account.get(i).getAccountNumber() == accountNumber) {
                account.get(i).setBalance(account.get(i).getBalance() + amount);
                System.out.println("Cash deposited successfully");
                return;
            }
        }
        System.out.println("Account not found!!");
    }

    public void withdrawCash(int accountNumber, double amount) {
        for (int i = 0; i < account.size(); i++) {
            if (account.get(i) != null && account.get(i).getAccountNumber() == accountNumber) {
                if (amount <= account.get(i).getBalance()) {
                    account.get(i).setBalance(account.get(i).getBalance() - amount);
                    System.out.println("cash withdrawed successfully");
                } else
                    System.out.println("insufficient Balance!!");
                return;
            }
        }
        System.out.println("Account not found!!");
    }

    public int displayAccountInfo(int accountNumber) {
        for (int i = 0; i < account.size(); i++) {
            if (account.get(i) != null && account.get(i).getAccountNumber() == accountNumber) {
                System.out.println("Account Information:");
                System.out.println("Account Number: " + account.get(i).getAccountNumber());
                System.out.println("Account Type: " + account.get(i).getAccountType());
                System.out.println("Balance: " + account.get(i).getBalance());
                System.out.println("\nAccount Holder Information:");
                System.out.println("Name: " + accountHolder[i].getName());
                System.out.println("Address: " + accountHolder[i].getAddress());
                System.out.println("Phone Number: " + accountHolder[i].getPhoneNumber());
                return i;
            }
        }
        return 0;
    }
}

