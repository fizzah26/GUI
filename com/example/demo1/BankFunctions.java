package com.example.demo1;
public interface BankFunctions {
    void depositCash(int accountNumber, double amount);

    void withdrawCash(int accountNumber, double amount);

    int displayAccountInfo(int accountNumber);

    void createAccount(BankAccount accounts, AccountHolder accountHolders);
}