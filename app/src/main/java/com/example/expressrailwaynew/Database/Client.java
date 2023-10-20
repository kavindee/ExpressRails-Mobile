package com.example.expressrailwaynew.Database;

import android.provider.BaseColumns;

public final class Client {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Client() {}

    /* Inner class that defines the table contents */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_nic = "nic";
        public static final String COLUMN_name = "name";
        public static final String COLUMN_age = "age";
        public static final String COLUMN_mobileNumber = "mobileNumber";

    }


}
