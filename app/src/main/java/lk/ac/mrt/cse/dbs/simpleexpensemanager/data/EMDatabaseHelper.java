package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.database.Cursor;

import java.util.Date;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public interface EMDatabaseHelper {

    Cursor getAccountNumbers();
    Cursor getAccounts();
    Cursor getAccount(String accountNo);
    void addAccount(Account account);
    void removeAccount(String accountNumber);
    void increaseBalance(String accountNumber, String amount);
    void decreaseBalance(String accountNumber, String amount);

    void addTransaction(String date, String accountNo, String expenseType, String amount);
    Cursor getAllTransactionLogs();
    Cursor getLimitedTransactionLogs(int limit);

}
