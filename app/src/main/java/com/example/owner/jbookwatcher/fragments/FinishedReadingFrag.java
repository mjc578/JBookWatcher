package com.example.owner.jbookwatcher.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.jbookwatcher.Book;
import com.example.owner.jbookwatcher.DateLibrary;
import com.example.owner.jbookwatcher.R;
import com.example.owner.jbookwatcher.adapters.BookListAdapter;
import com.example.owner.jbookwatcher.data.BookDbHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinishedReadingFrag extends Fragment {

    private final int LIST_IDENT = 1;
    private ArrayList<Book> bookList;
    private BookListAdapter bookAdapter;
    private ListView list;
    private TextView noBooks;
    private DateLibrary dl;
    private BookDbHelper dbHelper;

    public FinishedReadingFrag() {
        // Required empty public constructor
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View crView = inflater.inflate(R.layout.fragment_finished_reading, container, false);
        dl = new DateLibrary();

        //instance our database
        dbHelper = new BookDbHelper(getContext());
        //load list of books that belong to this list category
        bookList = dbHelper.getListEntries(LIST_IDENT);
        bookAdapter = new BookListAdapter(getContext(), bookList);

        list = crView.findViewById(R.id.finished_read_list_view);
        noBooks = crView.findViewById(R.id.finished_list_text_view);

        //set list adapter on book list if there are any in db
        if(!bookList.isEmpty()){
            noBooks.setVisibility(View.GONE);
            list.setAdapter(bookAdapter);
            list.setVisibility(View.VISIBLE);
        }
        setOnListListener();

        return crView;
    }

    @Override
    public void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void setOnListListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < list.getCount(); i++){
                    View v = list.getChildAt(i);
                    v.findViewById(R.id.selected_book_buttons).setVisibility(View.GONE);
                }
                if(view.findViewById(R.id.selected_book_buttons).getVisibility() == View.GONE){
                    view.findViewById(R.id.selected_book_buttons).setVisibility(View.VISIBLE);

                    setBookDeleteListener(view, position);
                    setBookEditListener(view, position);
                    setMoveListener(view, position);
                }
                else{
                    view.findViewById(R.id.selected_book_buttons).setVisibility(View.GONE);
                }
            }
        });
    }

    private void setBookDeleteListener(final View v, final int position){
        TextView deleteText = v.findViewById(R.id.selected_book_delete);
        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View vv) {
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Book b = bookList.remove(position);
                                dbHelper.deleteBook(b);
                                dbHelper.printDbToLog();
                                list.setAdapter(bookAdapter);
                                if(bookList.isEmpty()){
                                    noBooks.setVisibility(View.VISIBLE);
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Delete this book?").setPositiveButton("Yes", dialogListener)
                        .setNegativeButton("No", dialogListener).show();
            }
        });
    }
    */
}
