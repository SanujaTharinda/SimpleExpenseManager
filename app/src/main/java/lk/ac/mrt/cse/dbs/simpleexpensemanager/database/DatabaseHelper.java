package lk.ac.mrt.cse.dbs.simpleexpensemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.EMDatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;


public class DatabaseHelper extends SQLiteOpenHelper implements EMDatabaseHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "180635B.db";

    private static final String SQL_CREATE_ACCOUNT_TABLE =
            "CREATE TABLE IF NOT EXISTS " + EMContract.AccountEntry.TABLE_NAME + " (" +
                    EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_NUMBER + " TEXT PRIMARY KEY," +
                    EMContract.AccountEntry.COLUMN_NAME_BANK + " TEXT NOT NULL," +
                    EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER + " TEXT NOT NULL," +
                    EMContract.AccountEntry.COLUMN_NAME_BALANCE + " NUMERIC DEFAULT 0.00 CHECK (" + EMContract.AccountEntry.COLUMN_NAME_BALANCE + " >0))";

    private static final String SQL_CREATE_TRANSACTION_LOG_TABLE =
            "CREATE TABLE IF NOT EXISTS " + EMContract.TransactionLogEntry.TABLE_NAME + " (" +
                    EMContract.TransactionLogEntry.COLUMN_NAME_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EMContract.TransactionLogEntry.COLUMN_NAME_DATE + " TEXT NOT NULL," +
                    EMContract.TransactionLogEntry.COLUMN_NAME_ACCOUNT_NUMBER + " TEXT NOT NULL," +
                    EMContract.TransactionLogEntry.COLUMN_NAME_EXPENSE_TYPE + " TEXT NOT NULL," +
                    EMContract.TransactionLogEntry.COLUMN_NAME_AMOUNT + " NUMERIC DEFAULT 0.00," +
                   "FOREIGN KEY(" + EMContract.TransactionLogEntry.COLUMN_NAME_ACCOUNT_NUMBER + ") REFERENCES " + "(" + EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_NUMBER + ")" + ")";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Cursor getAccountNumbers() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columnsToReturn = {EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_NUMBER};
        return database.query(EMContract.AccountEntry.TABLE_NAME, columnsToReturn, null, null, null, null, null);
    }

    @Override
    public Cursor getAccounts() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columnsToReturn = {EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_NUMBER,EMContract.AccountEntry.COLUMN_NAME_BANK, EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER, EMContract.AccountEntry.COLUMN_NAME_BALANCE};
        return database.query(EMContract.AccountEntry.TABLE_NAME, columnsToReturn, null, null, null, null, null);
    }

    @Override
    public Cursor getAccount(String accountNo) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columnsToReturn = {EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_NUMBER,EMContract.AccountEntry.COLUMN_NAME_BANK, EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER, EMContract.AccountEntry.COLUMN_NAME_BALANCE};
        String selection = "account_number=?";
        String[] selectionArgs = {accountNo};
        return database.query(EMContract.AccountEntry.TABLE_NAME, columnsToReturn, selection, selectionArgs, null, null, null);
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_NUMBER,account.getAccountNo());
        contentValues.put(EMContract.AccountEntry.COLUMN_NAME_BANK, account.getBankName());
        contentValues.put(EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_HOLDER, account.getAccountHolderName());
        contentValues.put(EMContract.AccountEntry.COLUMN_NAME_BALANCE, account.getBalance());

        database.insert(EMContract.AccountEntry.TABLE_NAME, null, contentValues);
    }

    @Override
    public void removeAccount(String accountNumber) {
        SQLiteDatabase database = this.getWritableDatabase();
        String whereClause = "account_number=?";
        String[] whereArgs = { accountNumber };
        database.delete(EMContract.AccountEntry.TABLE_NAME, whereClause, whereArgs);
    }

    @Override
    public void increaseBalance(String accountNumber, String amount) {
        String query =
                "UPDATE " + EMContract.AccountEntry.TABLE_NAME + " SET " + EMContract.AccountEntry.COLUMN_NAME_BALANCE +
                        "=" + EMContract.AccountEntry.COLUMN_NAME_BALANCE + "+?" +
                        " WHERE " + EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_NUMBER + "=" + "?";
        String[] args = {amount, accountNumber};
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, args);
        cursor.moveToFirst();
        cursor.close();
    }

    @Override
    public void decreaseBalance(String accountNumber, String amount) {
        String query =
                "UPDATE " + EMContract.AccountEntry.TABLE_NAME + " SET " + EMContract.AccountEntry.COLUMN_NAME_BALANCE +
                        "=" + EMContract.AccountEntry.COLUMN_NAME_BALANCE + "-?" +
                        " WHERE " + EMContract.AccountEntry.COLUMN_NAME_ACCOUNT_NUMBER + "=" + "?";
        String[] args = {amount, accountNumber};
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, args);
        cursor.moveToFirst();
        cursor.close();
    }

    @Override
    public void addTransaction(String date, String accountNo, String expenseType, String amount) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EMContract.TransactionLogEntry.COLUMN_NAME_DATE, date);
        contentValues.put(EMContract.TransactionLogEntry.COLUMN_NAME_ACCOUNT_NUMBER, accountNo);
        contentValues.put(EMContract.TransactionLogEntry.COLUMN_NAME_EXPENSE_TYPE, expenseType);
        contentValues.put(EMContract.TransactionLogEntry.COLUMN_NAME_AMOUNT, amount);

        database.insert(EMContract.TransactionLogEntry.TABLE_NAME, null, contentValues);


    }

    @Override
    public Cursor getAllTransactionLogs() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columnsToReturn = {EMContract.TransactionLogEntry.COLUMN_NAME_DATE, EMContract.TransactionLogEntry.COLUMN_NAME_ACCOUNT_NUMBER, EMContract.TransactionLogEntry.COLUMN_NAME_EXPENSE_TYPE, EMContract.TransactionLogEntry.COLUMN_NAME_AMOUNT};
        return database.query(EMContract.TransactionLogEntry.TABLE_NAME, columnsToReturn, null, null, null, null, null);
    }

    @Override
    public Cursor getLimitedTransactionLogs(int limit) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columnsToReturn = {EMContract.TransactionLogEntry.COLUMN_NAME_DATE, EMContract.TransactionLogEntry.COLUMN_NAME_ACCOUNT_NUMBER, EMContract.TransactionLogEntry.COLUMN_NAME_EXPENSE_TYPE, EMContract.TransactionLogEntry.COLUMN_NAME_AMOUNT};
        return database.query(EMContract.TransactionLogEntry.TABLE_NAME, columnsToReturn, null, null, null, null, null, String.valueOf(limit));
    }

}
