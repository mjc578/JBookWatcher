package com.example.owner.jbookwatcher;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private UtilityLibrary ul;
    private BookDbHelper dbHelper;

    public ToReadFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View crView = inflater.inflate(R.layout.fragment_to_read, container, false);
        ul = new UtilityLibrary();

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

        setFabulousButton(crView);
        setOnListListener();

        return crView;
    }

    public AlertDialog makeAlertDLog(View v, String title){
        return new AlertDialog.Builder(getContext())
                .setView(v)
                .setTitle(title)
                .setPositiveButton("Done", null)
                .setNegativeButton("Cancel", null)
                .create();
    }

    public Book makeBook(String title, View dcrView){
        Book book = new Book(title, LIST_IDENT);
        //check for other fields
        EditText etAuthor = dcrView.findViewById(R.id.edit_text_author_to_read);
        if (!etAuthor.getText().toString().equals("")) {
            book.setAuthor(etAuthor.getText().toString());
        }
        EditText etPageNum = dcrView.findViewById(R.id.edit_text_book_pages_to_read);
        if (!etPageNum.getText().toString().equals("")) {
            book.setPageNum(Integer.parseInt(etPageNum.getText().toString()));
        }
        return book;
    }

    private void setFabulousButton(final View crView) {
        crView.findViewById(R.id.fab_to_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dcrView = View.inflate(getContext(), R.layout.to_read_dialog, null);
                final AlertDialog dLog = makeAlertDLog(dcrView, "Enter Book Info");
                dLog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        final Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                EditText etBookTitle = dcrView.findViewById(R.id.edit_text_book_title_to_read);
                                EditText etBookAuthor = dcrView.findViewById(R.id.edit_text_author_to_read);
                                if (!ul.editHasText(etBookTitle) || !ul.editHasText(etBookAuthor)) {
                                    Toast.makeText(getContext(), R.string.no_titleauth, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Book book = makeBook(etBookTitle.getText().toString(), dcrView);
                                //only proceed if can insert book into db
                                if(dbHelper.addBook(book)){
                                    bookList.add(book);
                                    dbHelper.addBook(book);
                                    dbHelper.printDbToLog();
                                    noBooks.setVisibility(View.GONE);
                                    list.setAdapter(bookAdapter);
                                }
                                else{
                                    Toast.makeText(getContext(), "Book already exists in one of your lists", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                //Dismiss once everything is OK.
                                dLog.dismiss();
                            }
                        });
                    }
                });
                dLog.show();
            }
        });
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

    private void setBookEditListener(final View v, final int position){
        final TextView editText = v.findViewById(R.id.selected_book_edit);
        final Book currBook = bookList.get(position);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                final View dcrView = View.inflate(getContext(), R.layout.to_read_dialog, null);
                final EditText etBookTitle = dcrView.findViewById(R.id.edit_text_book_title_to_read);
                final EditText etAuthor = dcrView.findViewById(R.id.edit_text_author_to_read);
                final EditText etPageNum = dcrView.findViewById(R.id.edit_text_book_pages_to_read);
                //fill edit texts with current data
                if(currBook.getAuthor() != null){
                    etAuthor.setText(currBook.getAuthor());
                }
                if(currBook.getPageNum() != -1){
                    etPageNum.setText(currBook.getPageNum() + "");
                }
                etBookTitle.setText(currBook.getBookTitle());
                final AlertDialog dLog = makeAlertDLog(dcrView, "Edit Book Info");
                dLog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        final Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                if (!ul.editHasText(etBookTitle) || !ul.editHasText(etAuthor)) {
                                    Toast.makeText(getContext(), R.string.no_titleauth, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                int i = bookList.indexOf(currBook);
                                Book book = makeBook(etBookTitle.getText().toString(), dcrView);
                                if(dbHelper.updateBook(currBook, book)){
                                    bookList.set(i, book);
                                    list.setAdapter(bookAdapter);
                                }
                                else{
                                    Toast.makeText(getContext(), getString(R.string.book_in_db), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //Dismiss once everything is OK.
                                dLog.dismiss();
                            }
                        });
                    }
                });
                dLog.show();
            }
        });
    }

    private void setMoveListener(final View view, final int position){
        //view is the view of the list item
        TextView moveButton = view.findViewById(R.id.selected_book_move);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Implement this when you implement database!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
