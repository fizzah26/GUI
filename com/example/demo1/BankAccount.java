package com.example.demo1;

import java.io.Serializable;
import java.util.ArrayList;

public class BankAccount implements Serializable {
    private   int accountNumber;
    private AccountType accountType;
    double balance=0;
    ArrayList<BankAccount> newAccount= new ArrayList<>();

    public BankAccount(AccountType accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
        newAccount.add(this);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public String toString(){
        return String.format("%d,%s,%f",accountNumber,accountType,balance);
    }
}




