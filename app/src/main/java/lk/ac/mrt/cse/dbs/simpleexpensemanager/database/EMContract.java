package lk.ac.mrt.cse.dbs.simpleexpensemanager.database;

import android.provider.BaseColumns;

public final class EMContract {


    private EMContract(){};

    public static class AccountEntry implements BaseColumns {
        public static final String TABLE_NAME = "account";
        public static final String COLUMN_NAME_ACCOUNT_NUMBER = "account_number";
        public static final String COLUMN_NAME_BANK = "bank";
        public static final String COLUMN_NAME_ACCOUNT_HOLDER = "account_holder";
        public static final String COLUMN_NAME_BALANCE = "balance";
    }

    public static class TransactionLogEntry implements BaseColumns {
        public static final String TABLE_NAME = "transaction_log";
        public static final String COLUMN_NAME_TRANSACTION_ID = "transaction_id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_ACCOUNT_NUMBER = "account_number";
        public static final String COLUMN_NAME_EXPENSE_TYPE = "type";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }

}
