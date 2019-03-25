package com.example.owner.jbookwatcher.data;

import android.provider.BaseColumns;

public final class BookContract {

    //prohibit class instantiation
    private BookContract(){}

    public static final class BookEntry implements BaseColumns{

        public static final String TABLE_NAME = "book_entry";
        public static final String COLUMN_BOOK_OLID = "olid";
        public static final String COLUMN_BOOK_NAME = "name";
        public static final String COLUMN_BOOK_AUTHOR = "author";
        public static final String COLUMN_PAGE_NUMBER = "page_count";
        public static final String COLUMN_DATE_STARTED = "date_started";
        public static final String COLUMN_DATE_FINISHED = "date_finished";
        public static final String COLUMN_LIST_INDICATOR = "list_indicator";

    }

}
