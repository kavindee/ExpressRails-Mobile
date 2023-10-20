package com.example.expressrailwaynew.Database;

import android.provider.BaseColumns;

public final class history {

    private history() {}

    /* Inner class that defines the table contents */
    public static class record implements BaseColumns {
        public static final String TABLE_NAME = "History";
        public static final String COLUMN_NAME_UserName = "UserName";
        public static final String COLUMN_NAME_TicketID = "TicketID";
        public static final String COLUMN_NAME_TotalPrice = "TotalPrice";

        public static final String COLUMN_NAME_Frm = "Frm";
        public static final String COLUMN_NAME_Too = "Too";

        public static final String COLUMN_NAME_Passengers = "Passenger";
        public static final String COLUMN_NAME_Date = "Date";

    };
};





