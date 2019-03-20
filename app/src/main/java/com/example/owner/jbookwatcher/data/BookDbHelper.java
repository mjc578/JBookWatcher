package com.example.owner.jbookwatcher.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.owner.jbookwatcher.data.BookContract.bookEntry;


public class BookDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BookEntry.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + bookEntry.TABLE_NAME + " (" +
                    bookEntry._ID + " INTEGER PRIMARY KEY," +
                    bookEntry.COLUMN_BOOK_NAME + " TEXT," +
                    bookEntry.COLUMN_BOOK_AUTHOR + " TEXT," +
                    bookEntry.COLUMN_PAGE_NUMBER + " INTEGER," +
                    bookEntry.COLUMN_DATE_STARTED + " TEXT," +
                    bookEntry.COLUMN_DATE_FINISHED + " TEXT," +
                    bookEntry.COLUMN_LIST_INDICATOR + ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + bookEntry.TABLE_NAME;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
