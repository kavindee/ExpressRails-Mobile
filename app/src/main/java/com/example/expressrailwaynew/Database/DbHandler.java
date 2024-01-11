package com.example.expressrailwaynew.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Express.db";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_RES);
        db.execSQL(SQL_CREATE_HISTORY);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_USER);
        db.execSQL(SQL_DELETE_RES);
        db.execSQL(SQL_DELETE_HISTORY);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    //Create user Table
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + userProfile.User.TABLE_NAME + " (" +
                    userProfile.User._ID + " INTEGER PRIMARY KEY," +
                    userProfile.User.COLUMN_nic + " TEXT," +
                    userProfile.User.COLUMN_name + " TEXT," +
                    userProfile.User.COLUMN_email + " TEXT," +
                    userProfile.User.COLUMN_mobileNumber + " TEXT," +
                    userProfile.User.COLUMN_password + " TEXT)";

    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + userProfile.User.TABLE_NAME;


    // Create reservation table

    private static final String SQL_CREATE_RES =
            "CREATE TABLE " + Reservation.Res.TABLE_NAME + " (" +
                    Reservation.Res._ID + " INTEGER PRIMARY KEY," +
                    Reservation.Res.COLUMN_NAME_From + " TEXT," +
                    Reservation.Res.COLUMN_NAME_To + " TEXT," +
                    Reservation.Res.COLUMN_NAME_Date + " TEXT," +
                    Reservation.Res.COLUMN_NAME_UserNIC + " TEXT," +
                    Reservation.Res.COLUMN_NAME_Passengers + " TEXT)";

    private static final String SQL_DELETE_RES =
            "DROP TABLE IF EXISTS " + Reservation.Res.TABLE_NAME;

    //Create history table

    private static final String SQL_CREATE_HISTORY =
            "CREATE TABLE " + history.record.TABLE_NAME + " (" +
                    history.record._ID + " INTEGER PRIMARY KEY," +
                    history.record.COLUMN_NAME_UserName + " TEXT," +
                    history.record.COLUMN_NAME_TicketID + " TEXT," +
                    history.record.COLUMN_NAME_TotalPrice + " TEXT," +
                    history.record.COLUMN_NAME_Frm + " TEXT," +
                    history.record.COLUMN_NAME_Too + " TEXT,"+
                    history.record.COLUMN_NAME_Passengers +" TEXT,"+
                    history.record.COLUMN_NAME_Date + " TEXT)";

    private static final String SQL_DELETE_HISTORY =
            "DROP TABLE IF EXISTS " + history.record.TABLE_NAME;

    // USER crud operations

    public long registerUser (String nic , String name , String email , String mobileNumber, String password){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(userProfile.User.COLUMN_nic, nic);
        values.put(userProfile.User.COLUMN_name, name);
        values.put(userProfile.User.COLUMN_email, email);
        values.put(userProfile.User.COLUMN_mobileNumber,mobileNumber );
        values.put(userProfile.User.COLUMN_password,password );

        long newRowId = db.insert(userProfile.User.TABLE_NAME, null, values);
        return newRowId;
    }

    // check user exist
    public boolean checkUserExistence(String nic) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                userProfile.User.COLUMN_nic
        };

        String selection = userProfile.User.COLUMN_nic + " = ?";
        String[] selectionArgs = {nic};

        Cursor cursor = db.query(
                userProfile.User.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean userExists = cursor.getCount() > 0;

        cursor.close();
        return userExists;
    }



//    public boolean updateUser (String name , String email , String mobileNumber){
//
//        SQLiteDatabase db = getWritableDatabase();
//// New value for one column
//
//        ContentValues values = new ContentValues();
////        values.put(userProfile.User.COLUMN_nic, nic);
//        values.put(userProfile.User.COLUMN_name, name);
//        values.put(userProfile.User.COLUMN_email, email);
//        values.put(userProfile.User.COLUMN_mobileNumber,mobileNumber );
//
//
//// Which row to update, based on the title
//        String selection = userProfile.User.COLUMN_nic + " LIKE ?";
//        String[] selectionArgs = { "MyOldTitle" };
//
//        int count = db.update(
//                userProfile.User.TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);
//        if(count >=1){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }

    public boolean updateUser(String name, String email, String mobileNumber, String oldNic) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(userProfile.User.COLUMN_name, name);
        values.put(userProfile.User.COLUMN_email, email);
        values.put(userProfile.User.COLUMN_mobileNumber, mobileNumber);

        String selection = userProfile.User.COLUMN_nic + " LIKE ?";
        String[] selectionArgs = { oldNic }; // Replace with the actual old NIC value you want to match.

        int count = db.update(
                userProfile.User.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        return count >= 1;
    }
//    public void deleteUser(String nic){
//        SQLiteDatabase db = getWritableDatabase();
//
//        // Define 'where' part of query.
//        String selection = userProfile.User.COLUMN_nic + " LIKE ?";
//// Specify arguments in placeholder order.
//        String[] selectionArgs = { nic };
//// Issue SQL statement.
//        int deletedRows = db.delete(userProfile.User.TABLE_NAME, selection, selectionArgs);
//
//    }

    public void deleteUser(String nic) {
        SQLiteDatabase db = getWritableDatabase();

        // Define 'where' part of query.
        String selection = userProfile.User.COLUMN_nic + " = ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = { nic };

        // Issue SQL statement to delete the user.
        int deletedRows = db.delete(userProfile.User.TABLE_NAME, selection, selectionArgs);
    }

    public List getUser() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                userProfile.User.COLUMN_nic,
                userProfile.User.COLUMN_name,
                userProfile.User.COLUMN_email,
                userProfile.User.COLUMN_mobileNumber,
                userProfile.User.COLUMN_password

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = userProfile.User.COLUMN_nic + " = ?";
        String[] selectionArgs = {"My Title"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                userProfile.User.COLUMN_nic + " DESC";

        Cursor cursor = db.query(
                userProfile.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List userNic = new ArrayList<>();
        while (cursor.moveToNext()) {
            String nic = cursor.getString(cursor.getColumnIndexOrThrow(userProfile.User.COLUMN_nic));
            userNic.add(nic);
        }
        cursor.close();
        return userNic;
    }

    public List getUserInfo( String nic) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                userProfile.User.COLUMN_nic,
                userProfile.User.COLUMN_name,
                userProfile.User.COLUMN_email,
                userProfile.User.COLUMN_mobileNumber,
                userProfile.User.COLUMN_password

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = userProfile.User.COLUMN_nic + " LIKE ?";
        String[] selectionArgs = { nic };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                userProfile.User.COLUMN_nic + " DESC";

        Cursor cursor = db.query(
                userProfile.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List userInfo = new ArrayList<>();
        while (cursor.moveToNext()) {
            String user_nic = cursor.getString(cursor.getColumnIndexOrThrow(userProfile.User.COLUMN_nic));
            String user_name = cursor.getString(cursor.getColumnIndexOrThrow(userProfile.User.COLUMN_name));
            String user_email = cursor.getString(cursor.getColumnIndexOrThrow(userProfile.User.COLUMN_email));
            String user_mobileNumber = cursor.getString(cursor.getColumnIndexOrThrow(userProfile.User.COLUMN_mobileNumber));
            String user_password = cursor.getString(cursor.getColumnIndexOrThrow(userProfile.User.COLUMN_password));

            userInfo.add(user_nic);
            userInfo.add(user_name);
            userInfo.add(user_email);
            userInfo.add(user_mobileNumber);
            userInfo.add(user_password);

        }
        cursor.close();
        return userInfo ;
    }
    // reservation  crud

    public long BookTicket ( String From , String To , String Date , String userNic, String passage){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reservation.Res.COLUMN_NAME_From, From);
        values.put(Reservation.Res.COLUMN_NAME_To, To);
        values.put(Reservation.Res.COLUMN_NAME_Date, Date);
        values.put(Reservation.Res.COLUMN_NAME_UserNIC, userNic);
        values.put(Reservation.Res.COLUMN_NAME_Passengers, passage);


        long newRow = db.insert(Reservation.Res.TABLE_NAME, null, values);
        return newRow;
    }

    public void  DeleteBooking ( Integer _ID){
        SQLiteDatabase db = getWritableDatabase();


        String selection = Reservation.Res._ID + " LIKE ?";

        String[] selectionArgs = { String.valueOf(_ID) };

        int deletedRows = db.delete(Reservation.Res.TABLE_NAME, selection, selectionArgs);
    }

    public List ViewBooking(String id){
        SQLiteDatabase db = getReadableDatabase();


        String[] projection = {
                BaseColumns._ID,
                Reservation.Res.COLUMN_NAME_From,
                Reservation.Res.COLUMN_NAME_To,
                Reservation.Res.COLUMN_NAME_Date,
                Reservation.Res.COLUMN_NAME_Passengers
        };


        String selection = Reservation.Res._ID + " = ?";
        String[] selectionArgs = { id  };



        Cursor cursor = db.query(
                Reservation.Res.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List ticketInfo = new ArrayList<>();
        while (cursor.moveToNext()) {
            String from = cursor.getString(cursor.getColumnIndexOrThrow(Reservation.Res.COLUMN_NAME_From));
            String to = cursor.getString(cursor.getColumnIndexOrThrow(Reservation.Res.COLUMN_NAME_To));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(Reservation.Res.COLUMN_NAME_Date));
            String passengers = cursor.getString(cursor.getColumnIndexOrThrow(Reservation.Res.COLUMN_NAME_Passengers));
            ticketInfo.add(from);
            ticketInfo.add(to);
            ticketInfo.add(date);
            ticketInfo.add(passengers);

        }
        cursor.close();
        return ticketInfo ;

    }

    // insert history

    public long addHistoryRecord(String userName, long ticketID, double totalPrice,String from,String to,String passenger, String date) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(history.record.COLUMN_NAME_UserName, userName);
        values.put(history.record.COLUMN_NAME_TicketID, ticketID);
        values.put(history.record.COLUMN_NAME_TotalPrice, totalPrice);
        values.put(history.record.COLUMN_NAME_Frm, from);
        values.put(history.record.COLUMN_NAME_Too, to);
        values.put(history.record.COLUMN_NAME_Passengers, passenger);
        values.put(history.record.COLUMN_NAME_Date, date);

        long newRowId = db.insert(history.record.TABLE_NAME, null, values);
        return newRowId;
    }

    //view history

    public List<String> getAllHistoryData() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                history.record.COLUMN_NAME_UserName,
                history.record.COLUMN_NAME_TicketID,
                history.record.COLUMN_NAME_TotalPrice,
                history.record.COLUMN_NAME_Frm,
                history.record.COLUMN_NAME_Too,
                history.record.COLUMN_NAME_Passengers,
                history.record.COLUMN_NAME_Date
        };

        Cursor cursor = db.query(
                history.record.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<String> historyData = new ArrayList<>();
        while (cursor.moveToNext()) {
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(history.record.COLUMN_NAME_UserName));
            long ticketID = cursor.getLong(cursor.getColumnIndexOrThrow(history.record.COLUMN_NAME_TicketID));
            double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(history.record.COLUMN_NAME_TotalPrice));
            String from = cursor.getString(cursor.getColumnIndexOrThrow(history.record.COLUMN_NAME_Frm));
            String to = cursor.getString(cursor.getColumnIndexOrThrow(history.record.COLUMN_NAME_Too));
            String passenger = cursor.getString(cursor.getColumnIndexOrThrow(history.record.COLUMN_NAME_Passengers));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(history.record.COLUMN_NAME_Date));

            String historyEntry = "TicketID: " + ticketID + "\nTotalPrice: " + totalPrice +"\nFrom: " + from +"\nTo: " + to +"\nPassenger: " + passenger + "\nDate: " + date;
            historyData.add(historyEntry);
        }
        cursor.close();
        return historyData;
    }
}
