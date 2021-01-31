package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.EMDatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {
    private EMDatabaseHelper databaseHelper;

    public PersistentAccountDAO(EMDatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> accountNumbers = new ArrayList<>();
        Cursor dataCursor = this.databaseHelper.getAccountNumbers();
        while (dataCursor.moveToNext()){
            String accountNumber = dataCursor.getString(0);
            accountNumbers.add(accountNumber);
        }
        return accountNumbers;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accounts = new ArrayList<>();
        Cursor dataCursor = this.databaseHelper.getAccounts();
        while (dataCursor.moveToNext()){
            String accountNumber = dataCursor.getString(0);
            String bank = dataCursor.getString(1);
            String accountHolder = dataCursor.getString(2);
            double balance = Double.parseDouble(dataCursor.getString(3));
            Account account = new Account(accountNumber, bank, accountHolder, balance);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account account = null;

        Cursor dataCursor = this.databaseHelper.getAccount(accountNo);
        if(dataCursor.getCount() != 0) {
            dataCursor.moveToFirst();
            String accountNumber = dataCursor.getString(0);
            String bank = dataCursor.getString(1);
            String accountHolder = dataCursor.getString(2);
            double balance = Double.parseDouble(dataCursor.getString(3));
            account = new Account(accountNumber, bank, accountHolder, balance);
        }

        return account;
    }

    @Override
    public void addAccount(Account account) {
        databaseHelper.addAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        databaseHelper.removeAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
         if(expenseType == ExpenseType.INCOME){
             databaseHelper.increaseBalance(accountNo, String.valueOf(amount));
             return;
         }
         databaseHelper.decreaseBalance(accountNo, String.valueOf(amount));
    }
}
