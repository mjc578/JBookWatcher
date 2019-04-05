package com.example.owner.jbookwatcher.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class ToReadFrag extends Fragment {

    private final int LIST_IDENT = 2;
    private ArrayList<Book> bookList;
    private BookListAdapter bookAdapter;
    private ListView list;
    private TextView noBooks;
    private DateLibrary ul;
    private BookDbHelper dbHelper;

    public ToReadFrag() {
        // Required empty public constructor
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View crView = inflater.inflate(R.layout.fragment_to_read, container, false);
        ul = new DateLibrary();

        //instance our database
        dbHelper = new BookDbHelper(getContext());
        //load list of books that belong to this list category
        bookList = dbHelper.getListEntries(LIST_IDENT);
        bookAdapter = new BookListAdapter(getContext(), bookList);

        list = crView.findViewById(R.id.to_read_list_view);
        noBooks = crView.findViewById(R.id.to_read_list_text_view);

        //set list adapter on book list if there are any in db
        if(!bookList.isEmpty()){
            noBooks.setVisibility(View.GONE);
            list.setAdapter(bookAdapter);
            list.setVisibility(View.VISIBLE);
        }

        setOnListListener();

        return crView;
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
    */
}
