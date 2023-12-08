package com.example.demo1;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class BankSystem {
    public static void main(String[] args) {
        Bank bank = new Bank();
        File file=new File("bank.txt");
        FileWriter writer=null;
        try{
            file.createNewFile();
            writer = new FileWriter("bank.txt", true);
        }
        catch(IOException e){
            System.out.println("exception occured!");
            e.printStackTrace();
        }

        System.out.println("choose an option:");
        Scanner sc = new Scanner(System.in);
        System.out.println("1-create new bank account");
        System.out.println("2-Deposit cash");
        System.out.println("3-Withdraw cash");
        System.out.println("4-Display account info");
        System.out.println("5.Exit");
        int option = sc.nextInt();
        sc.nextLine();
        while (option != 5) {
            switch (option) {
                case 1:
                    System.out.println("Enter your full name:");
                    String name = sc.nextLine();
                    System.out.println("Enter address:");
                    String add = sc.nextLine();
                    System.out.println("Enter phone number:");
                    String num = sc.nextLine();
                    System.out.println("Enter the desired account type:");
                    AccountType accType = AccountType.valueOf(sc.nextLine().toUpperCase());
                    System.out.println("enter amount you want to deposit");
                    double balance = sc.nextDouble();
                    BankAccount b1 = new BankAccount(accType, balance);
                    AccountHolder a1 = new AccountHolder(name, add, num);
                    bank.createAccount(b1, a1);
                    saveAccountToFile(writer, b1, a1);
                    break;
                case 2:
                    System.out.println("Enter account no:");
                    int depAcc = sc.nextInt();
                    System.out.println("Enter  the amount you want to deposit:");
                    double depAmount = sc.nextDouble();
                    bank.depositCash(depAcc, depAmount);
                    break;
                case 3:
                    System.out.println("Enter account no:");
                    int withdrawAcc = sc.nextInt();
                    System.out.println("Enter  the amount you want to withdraw:");
                    double withdrawAmount = sc.nextDouble();
                    bank.withdrawCash(withdrawAcc, withdrawAmount);
                    break;
                case 4:
                    System.out.println("Enter your account number:");
                    int displayAcc= sc.nextInt();
                    bank.displayAccountInfo(displayAcc);
                    break;
                case 5:
                    System.out.println("Exiting the bank");
                    break;
                default:
                    System.out.println("invalid option!!");
                    break;
            }
            System.out.println("Choose an option:");
            option=sc.nextInt();
            sc.nextLine();
        }
        try {
            writer.close();
        }
        catch (IOException e){
            System.out.println("exception occured!!");
            e.printStackTrace();
        }

        sc.close();
    }

    private static void saveAccountToFile(FileWriter writer, BankAccount account, AccountHolder accountHolder) {
        try {
            writer.append(account.toString()).append(",").append(accountHolder.toString()).append("\n");
            writer.flush();

        } catch (IOException e) {
            System.out.println("Exception occurred while writing to the file!");
            e.printStackTrace();
        }
    }

}




