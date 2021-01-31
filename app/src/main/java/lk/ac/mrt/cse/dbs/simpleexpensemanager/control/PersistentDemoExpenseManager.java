package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.EMDatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.DatabaseHelper;

public class PersistentDemoExpenseManager extends ExpenseManager {
    private Context context;

    public PersistentDemoExpenseManager(Context context) throws ExpenseManagerException {
        this.context = context;
        setup();
    }

    @Override
    public void setup() throws ExpenseManagerException {
        EMDatabaseHelper databaseHelper = new DatabaseHelper(this.context);
        TransactionDAO transactionDAO = new PersistentTransactionDAO(databaseHelper);
        setTransactionsDAO(transactionDAO);
        AccountDAO accountDAO = new PersistentAccountDAO(databaseHelper);
        setAccountsDAO(accountDAO);

        // dummy data
//        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
//        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
//        getAccountsDAO().addAccount(dummyAcct1);
//        getAccountsDAO().addAccount(dummyAcct2);

    }

}
