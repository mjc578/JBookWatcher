package com.example.owner.jbookwatcher.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.jbookwatcher.Book;
import com.example.owner.jbookwatcher.BookAddedDetailActivity;
import com.example.owner.jbookwatcher.DateLibrary;
import com.example.owner.jbookwatcher.R;
import com.example.owner.jbookwatcher.adapters.BookAdapter;
import com.example.owner.jbookwatcher.data.BookDbHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToReadFrag extends Fragment {

    private final int LIST_IDENT = 2;
    private ArrayList<Book> bookList;
    private BookAdapter bookAdapter;
    private ListView list;
    private TextView noBooks;
    private DateLibrary ud;
    private BookDbHelper dbHelper;
    private String BOOK_DETAIL_KEY = "book";

    public ToReadFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View crView = inflater.inflate(R.layout.shared_list_view_book_list, container, false);
        ud = new DateLibrary();

        //instance our database
        dbHelper = new BookDbHelper(getContext());
        //load list of books that belong to this list category
        bookList = dbHelper.getListEntries(LIST_IDENT);
        bookAdapter = new BookAdapter(getContext(), bookList);

        list = crView.findViewById(R.id.shared_list_view);
        noBooks = crView.findViewById(R.id.shared_list_text_view);

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

                    setBookDetailListener(view, position);
                    setBookDeleteListener(view, position);
                    setMoveListener(view, position);
                }
                else{
                    view.findViewById(R.id.selected_book_buttons).setVisibility(View.GONE);
                }
            }
        });
    }

    private void setBookDetailListener(View view, final int position){
        TextView tvBookDetailButton = view.findViewById(R.id.entry_details_button);
        tvBookDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BookAddedDetailActivity.class);
                i.putExtra(BOOK_DETAIL_KEY, bookAdapter.getItem(position));
                startActivity(i);
            }
        });
    }

    private void setBookDeleteListener(View view, final int position){
        TextView tvBookDeleteButton = view.findViewById(R.id.entry_delete_button);
        tvBookDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Book b = bookList.remove(position);
                                dbHelper.deleteBook(b);
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
                builder.setMessage("Delete this Book?").setPositiveButton("Yes", dialogListener)
                        .setNegativeButton("No", dialogListener).show();

            }
        });
    }

    private void setMoveListener(View view, int position){

    }

}
