package com.example.owner.jbookwatcher.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.owner.jbookwatcher.Book;
import com.example.owner.jbookwatcher.data.BookContract.BookEntry;

import java.util.ArrayList;
import java.util.BitSet;


public class BookDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BookEntry.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                    BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BookEntry.COLUMN_BOOK_NAME + " TEXT," +
                    BookEntry.COLUMN_BOOK_AUTHOR + " TEXT," +
                    BookEntry.COLUMN_PAGE_NUMBER + " INTEGER," +
                    BookEntry.COLUMN_DATE_STARTED + " TEXT," +
                    BookEntry.COLUMN_DATE_FINISHED + " TEXT," +
                    BookEntry.COLUMN_LIST_INDICATOR + ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;

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

    public ArrayList<Book> getListEntries(int listIndicator){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + BookEntry.TABLE_NAME +
                " WHERE " + BookEntry.COLUMN_LIST_INDICATOR +
                "='" + listIndicator;

        Cursor c = db.rawQuery(query, null);
        ArrayList<Book> list = new ArrayList<>();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String bName = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_BOOK_NAME));
            String bAuthor = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_BOOK_AUTHOR));
            int bPagNum = c.getInt(c.getColumnIndexOrThrow(BookEntry.COLUMN_PAGE_NUMBER));
            String bStart = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_DATE_STARTED));
            String bEnd = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_DATE_FINISHED));
            int bIndicator = c.getInt(c.getColumnIndexOrThrow(BookEntry.COLUMN_LIST_INDICATOR));
            Book b = new Book(bName, bAuthor, bPagNum, bStart, bEnd, bIndicator);
            list.add(b);
        }
        c.close();
        return list;
    }

    public void addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(BookEntry.COLUMN_BOOK_NAME, book.getBookTitle());
        vals.put(BookEntry.COLUMN_BOOK_AUTHOR, book.getAuthor());
        vals.put(BookEntry.COLUMN_PAGE_NUMBER, book.getPageNum());
        vals.put(BookEntry.COLUMN_DATE_STARTED, book.getStartDate());
        vals.put(BookEntry.COLUMN_DATE_FINISHED,book.getEndDate());
        vals.put(BookEntry.COLUMN_LIST_INDICATOR, book.getListIndicator());
        long id = db.insert(DATABASE_NAME, null, vals);
    }

    public void deleteBook(){

    }

}
