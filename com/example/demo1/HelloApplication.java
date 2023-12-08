package com.example.demo1;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.*;

public class HelloApplication extends Application {
    private Bank b = new Bank();
    private TextField nameField, balanceField, addressField,accounttypeField,phoneno,accountno;
    private Label balanceValidation, acountInfo;
    AccountType accountType;
    Button btn1, btn2, btn3, btn4, btn5, btn6,btn7, btn8,btn9;
    int selectedAccountNumber;

    @Override
    public void start(Stage stage) throws IOException {
        b=loadBankFromFile();
        defineButtons();

        btn1.setOnAction(e -> {
            showAccountField(stage);
        });

        btn2.setOnAction(e ->{
            showDepositFields(stage);
        });
        btn3.setOnAction(e->{
            showwithdrawFields(stage);
        });
        btn4.setOnAction(e ->{
            showAccountDetails(stage, -1);
        });
        btn5.setOnAction(e -> {
            setHomePage(stage);
        });

        btn6.setOnAction(e -> {
            createAccount();
        });

        btn7.setOnAction(e -> {
            depositCash();
        });

        btn8.setOnAction(e ->{
            int i = b.displayAccountInfo(selectedAccountNumber);
            showAccountDetails(stage, i);

        });
        btn9.setOnAction(e -> {
            withdrawCah();
        });

        stage.setOnCloseRequest(event -> {
            saveBankToFile(b);
        });


        setHomePage(stage);
        stage.setTitle("BANKING SYSTEM");
        stage.show();
    }
    public static void main(String[] args) {

        launch();
    }
    private Bank loadBankFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank_data.dat"))) {
            return (Bank) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading bank data. Creating a new Bank object.");
            return new Bank();
        }
    }

    private void saveBankToFile(Bank bank) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bank_data.dat"))) {
            oos.writeObject(bank);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving bank data.");
        }
    }


    public void withdrawCah() {
        double balance;
        try {
            balance = Double.parseDouble(balanceField.getText());
            balanceValidation.setText("");
        } catch (Exception e) {
            balanceValidation.setText("Please input correct balance value");
            return;
        }
        b.withdrawCash(selectedAccountNumber, balance);
    }
    public void depositCash(){
        double balance;
        try {
            balance = Double.parseDouble(balanceField.getText());
            balanceValidation.setText("");
        }catch (Exception e)
        {
            balanceValidation.setText("Please input correct balance value");
            return;
        }
        b.depositCash(selectedAccountNumber,balance);
    }

    public void createAccount() {
        double balance;
        try {
            balance = Double.parseDouble(balanceField.getText());
            balanceValidation.setText("");
        }catch (Exception e)
        {
            balanceValidation.setText("Please input correct balance value");
            return;
        }
        BankAccount acc = new BankAccount(accountType, balance);
        AccountHolder accountHolders = new AccountHolder(nameField.getText(), addressField.getText(), phoneno.getText());
        b.createAccount(acc, accountHolders);
    }
    public void setHomePage(Stage stage){
        VBox root=new VBox(12.0);

        if (!root.getChildren().contains(btn1)) {
            root.getChildren().addAll(btn1, btn2, btn3, btn4);
        }
        Scene scene=new Scene(root,500,300);
        stage.setScene(scene);
    }

    public void showAccountField(Stage stage){
        nameField = new TextField();
        nameField.setPromptText("Enter your name here...");
        balanceField = new TextField();
        balanceField.setPromptText("Enter balance to deposit : ");
        balanceValidation = new Label();
        addressField = new TextField();
        addressField.setPromptText("Enter Address : ");
        ComboBox<AccountType> dropdown = new ComboBox<>();
        dropdown.setPromptText("Select Account Type");
        dropdown.setItems(FXCollections.observableArrayList(AccountType.values()));
        phoneno=new TextField();
        phoneno.setPromptText("enter your phone number: ");

        dropdown.setOnAction(e -> {
            accountType = dropdown.getValue();
        });

        VBox root1=new VBox(12.0);

        if (!root1.getChildren().contains(btn5)) {
            root1.getChildren().addAll(btn5, nameField, balanceField, addressField, dropdown, phoneno, btn6);
            Scene scene2=new Scene(root1,500,300);
            stage.setScene(scene2);
        }
    }

    public void showDepositFields(Stage stage){
        ComboBox<Integer> dropdown =accountNumberField();
        balanceField = new TextField();
        balanceField.setPromptText("Enter balance to deposit : ");

        VBox root=new VBox(12.0);
        root.getChildren().addAll(btn5, dropdown, balanceField, btn7);
        Scene scene2=new Scene(root,500,300);//scene created with stackpane
        stage.setScene(scene2);//scene is set on stage

    }
    public void showwithdrawFields(Stage stage){
        ComboBox<Integer> dropdown =accountNumberField();
        balanceField = new TextField();
        balanceField.setPromptText("Enter balance to withdraw : ");
        VBox root=new VBox(12.0);//layout created
        root.getChildren().addAll(btn5, dropdown, balanceField, btn9);
        Scene scene2=new Scene(root,500,300);//scene created with stackpane
        stage.setScene(scene2);//scene is set on stage
    }

    public void showAccountDetails(Stage stage, int i){
        ComboBox<Integer> dropdown =accountNumberField();
        VBox root=new VBox(12.0);//layout created
        if (i >-1) {
            BankAccount account = b.getAccounts().get(i);
            AccountHolder holder=new AccountHolder(nameField.getText(),addressField.getText(),phoneno.getText() );
            acountInfo = new Label("Account Information: \nAccount Number: " + account.getAccountNumber()+"\nAccount Type: "+account.getAccountType()+"\nBalance: "+account.getBalance()+"\n\nAccountHolder Details: \nName: "+holder.getName()+"\n Phone number: "+holder.getPhoneNumber()+"\n Address: "+holder.getAddress());
        }
        else{
            acountInfo = new Label();
        }
        root.getChildren().addAll(btn5, dropdown, btn8, acountInfo);

        Scene scene2=new Scene(root,500,300);//scene created with stackpane
        stage.setScene(scene2);//scene is set on stage

    }

    public ComboBox<Integer> accountNumberField(){
        Integer[] accounts = new Integer[b.getAccounts().size()];
        for (int i = 0; i < b.getAccounts().size(); i++) {
            accounts[i] = b.getAccounts().get(i).getAccountNumber();
        }
        ComboBox<Integer> dropdown = new ComboBox<>();
        dropdown.setPromptText("Select Account Number");
        dropdown.setItems(FXCollections.observableArrayList(accounts));
        dropdown.setOnAction(e -> {
            selectedAccountNumber = dropdown.getValue();
        });

        return dropdown;
    }

    public void defineButtons(){
        btn1 = new Button("1-CREATE BANKACCOUNT");
        btn2 = new Button("2-DEPOSIT CASH");
        btn3 = new Button("3-WITHDRAW CASH");
        btn4 = new Button("4-DISPLAY ACCOUNT INFORMATION");
        btn5 = new Button("Back Button");
        btn6 = new Button("Create Account");
        btn7 = new Button("Depoit cash");
        btn8 = new Button("View Details");
        btn9=new Button("withdraw cash");
    }
}