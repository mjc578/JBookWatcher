package com.example.owner.jbookwatcher;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentlyReadingFrag extends Fragment {

    private ArrayList<Book> bookList;
    private BookListAdapter bookAdapter;
    private ListView list;
    private TextView noBooks;

    public CurrentlyReadingFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View crView = inflater.inflate(R.layout.fragment_currently_reading, container, false);

        //TODO: gonna have to load books from database or whatever not this this erases it each load
        bookList = new ArrayList<>();
        bookAdapter = new BookListAdapter(getContext(), bookList);

        list = crView.findViewById(R.id.curr_read_list_view);
        noBooks = crView.findViewById(R.id.curr_list_text_view);

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
        Book book = new Book(title);
        //check for other fields
        EditText etAuthor = dcrView.findViewById(R.id.edit_text_author_curr);
        if (!etAuthor.getText().toString().equals("")) {
            book.setAuthor(etAuthor.getText().toString());
        }
        EditText etPageNum = dcrView.findViewById(R.id.edit_text_book_pages_curr);
        if (!etPageNum.getText().toString().equals("")) {
            book.setPageNum(Integer.parseInt(etPageNum.getText().toString()));
        }
        EditText etStartDate = dcrView.findViewById(R.id.edit_text_curr_read_start_date);
        if (!etStartDate.getText().toString().equals("")) {
            book.setStartDate(etStartDate.getText().toString());
        }
        return book;
    }

    private void setFabulousButton(final View crView) {
        crView.findViewById(R.id.fab_curr_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dcrView = View.inflate(getContext(), R.layout.currently_reading_dialog, null);
                //TODO: FIX THE BROKEN DOUBLE CLICK ON DATE EDIT TEXT
                dcrView.findViewById(R.id.edit_text_curr_read_start_date).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(dcrView);
                    }
                });
                final AlertDialog dLog = makeAlertDLog(dcrView, "Enter Book Info");
                dLog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        final Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                EditText etBookTitle = dcrView.findViewById(R.id.edit_text_book_title_curr);
                                String enteredTitle = etBookTitle.getText().toString();
                                if (enteredTitle.equals("")) {
                                    Toast.makeText(getContext(), "No book title entered", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Book book = makeBook(enteredTitle, dcrView);
                                bookList.add(book);
                                noBooks.setVisibility(View.GONE);
                                list.setAdapter(bookAdapter);

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

    private void datePicker(final View dcrView) {
        final Calendar myCalendar = Calendar.getInstance();

        final EditText dateET = dcrView.findViewById(R.id.edit_text_curr_read_start_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateET.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setOnListListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                                bookList.remove(position);
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
                final View dcrView = View.inflate(getContext(), R.layout.currently_reading_dialog, null);
                //fill edit texts with current data
                if(currBook.getStartDate() != null){
                    EditText etStartDate = dcrView.findViewById(R.id.edit_text_curr_read_start_date);
                    etStartDate.setText(currBook.getStartDate());
                }
                if(currBook.getAuthor() != null){
                    EditText etAuthor = dcrView.findViewById(R.id.edit_text_author_curr);
                    etAuthor.setText(currBook.getAuthor());
                }
                if(currBook.getPageNum() != -1){
                    EditText etPageNum = dcrView.findViewById(R.id.edit_text_book_pages_curr);
                    etPageNum.setText(currBook.getPageNum() + "");
                }
                final EditText etBookTitle = dcrView.findViewById(R.id.edit_text_book_title_curr);
                etBookTitle.setText(currBook.getBookTitle());
                //TODO: FIX THE BROKEN DOUBLE CLICK ON DATE EDIT TEXT
                dcrView.findViewById(R.id.edit_text_curr_read_start_date).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePicker(dcrView);
                    }
                });
                final AlertDialog dLog = makeAlertDLog(dcrView, "Edit Book Info");
                dLog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        final Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                String enteredTitle = etBookTitle.getText().toString();
                                if (enteredTitle.equals("")) {
                                    Toast.makeText(getContext(), "No book title entered", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                bookList.remove(currBook);
                                Book book = makeBook(currBook.getBookTitle(), dcrView);
                                bookList.add(book);
                                list.setAdapter(bookAdapter);

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