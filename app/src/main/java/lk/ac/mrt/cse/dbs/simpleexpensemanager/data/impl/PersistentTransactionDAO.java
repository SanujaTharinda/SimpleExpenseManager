package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.EMDatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {
    private EMDatabaseHelper databaseHelper;

    public PersistentTransactionDAO(EMDatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        databaseHelper.addTransaction(date.toString(), accountNo, expenseType.toString(), String.valueOf(amount));
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions = new ArrayList<>();
        Cursor dataCursor = this.databaseHelper.getAllTransactionLogs();
        while (dataCursor.moveToNext()){

            SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-YYYY");
            Date date = null;
            try {
                date = formatter.parse(dataCursor.getString(1));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String accountNumber = dataCursor.getString(2);
            ExpenseType expenseType = dataCursor.getString(3) == ExpenseType.EXPENSE.toString() ? ExpenseType.EXPENSE : ExpenseType.INCOME;
            double amount = Double.parseDouble(dataCursor.getString(4));
            Transaction transaction = new Transaction(date, accountNumber, expenseType, amount);
            transactions.add(transaction);
        }
        return transactions;

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = new ArrayList<>();
        Cursor dataCursor = this.databaseHelper.getLimitedTransactionLogs(limit);
        while (dataCursor.moveToNext()){

            SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-YYYY");
            Date date = null;
            try {
                date = new Date(dataCursor.getString(0));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String accountNumber = dataCursor.getString(1);
            ExpenseType expenseType = dataCursor.getString(2) == ExpenseType.EXPENSE.toString() ? ExpenseType.EXPENSE : ExpenseType.INCOME;
            double amount = Double.parseDouble(dataCursor.getString(3));
            Transaction transaction = new Transaction(date, accountNumber, expenseType, amount);
            transactions.add(transaction);
        }
        return transactions;
    }
}
