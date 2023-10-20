package com.example.expressrailwaynew.Database;

import android.provider.BaseColumns;

public final class Reservation {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Reservation() {}

    /* Inner class that defines the table contents */
    public static class Res implements BaseColumns {
        public static final String TABLE_NAME = "Reservation";
        public static final String COLUMN_NAME_From = "RFrom";
        public static final String COLUMN_NAME_To = "RTo";
        public static final String COLUMN_NAME_Date = "RDate";
        public static final String COLUMN_NAME_UserNIC = "userNic";
        public static final String COLUMN_NAME_Passengers = "passage";

    };
};
