package com.example.owner.jbookwatcher.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.owner.jbookwatcher.Book;
import com.example.owner.jbookwatcher.data.BookContract.BookEntry;

import java.util.ArrayList;

//TODO maybe support comparisons by lowercase

public class BookDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BookEntry.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                    BookEntry.COLUMN_BOOK_OLID + " TEXT," +
                    BookEntry.COLUMN_BOOK_NAME + " TEXT," +
                    BookEntry.COLUMN_BOOK_AUTHOR + " TEXT," +
                    BookEntry.COLUMN_PAGE_NUMBER + " INTEGER," +
                    BookEntry.COLUMN_DATE_STARTED + " TEXT," +
                    BookEntry.COLUMN_DATE_FINISHED + " TEXT," +
                    BookEntry.COLUMN_LIST_INDICATOR + " INTEGER," +
                    "PRIMARY KEY (" + BookEntry.COLUMN_BOOK_NAME + ", " + BookEntry.COLUMN_BOOK_AUTHOR + "));";

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
                "=" + listIndicator;

        Cursor c = db.rawQuery(query, null);
        ArrayList<Book> list = new ArrayList<>();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String bOlid = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_BOOK_OLID));
            String bName = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_BOOK_NAME));
            String bAuthor = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_BOOK_AUTHOR));
            int bPagNum = c.getInt(c.getColumnIndexOrThrow(BookEntry.COLUMN_PAGE_NUMBER));
            String bStart = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_DATE_STARTED));
            String bEnd = c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_DATE_FINISHED));
            int bIndicator = c.getInt(c.getColumnIndexOrThrow(BookEntry.COLUMN_LIST_INDICATOR));
            Book b = new Book(bOlid, bName, bAuthor, bStart, bEnd, bIndicator, bPagNum);
            list.add(b);
        }
        c.close();
        return list;
    }

    public boolean addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        //don't insert book if it already exists in ANY of the three lists
        if(checkIfDuplicate(book)){
            return false;
        }
        ContentValues vals = new ContentValues();
        vals.put(BookEntry.COLUMN_BOOK_OLID, book.getOLID());
        vals.put(BookEntry.COLUMN_BOOK_NAME, book.getBookTitle());
        vals.put(BookEntry.COLUMN_BOOK_AUTHOR, book.getAuthor());
        vals.put(BookEntry.COLUMN_PAGE_NUMBER, book.getPageNum());
        vals.put(BookEntry.COLUMN_DATE_STARTED, book.getStartDate());
        vals.put(BookEntry.COLUMN_DATE_FINISHED,book.getEndDate());
        vals.put(BookEntry.COLUMN_LIST_INDICATOR, book.getListIndicator());
        db.insert(BookEntry.TABLE_NAME, null, vals);
        return true;
    }

    public void printDbToLog(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + BookEntry.TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        if(c.getCount() == 0){
            Log.e("DATABASE STATUS", "EMPTY");
        }
        else{
            Log.e("DATABASE STATUS", "POPULATED");
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                Log.e("Book name", c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_BOOK_NAME)));
                Log.e("Corresponding List", c.getInt(c.getColumnIndexOrThrow(BookEntry.COLUMN_LIST_INDICATOR)) + "");
            }
            Log.e("DATABASE STATUS", "END OF ENTRIES");
        }
        c.close();
    }

    public void deleteBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = BookEntry.COLUMN_BOOK_OLID + " = ?";
        String[] whereArgs = new String[] { book.getOLID() };
        db.delete(BookEntry.TABLE_NAME, whereClause, whereArgs);
    }

    //method to query into db to see if a book already exists in any list
    private boolean checkIfDuplicate(Book book){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + BookEntry.COLUMN_BOOK_NAME +
                ", " + BookEntry.COLUMN_BOOK_AUTHOR +
                " FROM " + BookEntry.TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        boolean bool = false;
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String bn = removePunc(c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_BOOK_NAME)).toUpperCase());
            String an = removePunc(c.getString(c.getColumnIndexOrThrow(BookEntry.COLUMN_BOOK_AUTHOR)).toUpperCase());
            if(bn.matches(removePunc(book.getBookTitle().toUpperCase())) && an.matches(removePunc(book.getAuthor().toUpperCase()))){
                bool = true;
                break;
            }
        }
        c.close();
        return bool;
    }

    private String removePunc(String s){
        return s.replaceAll("\\p{Punct}", "");
    }

}
