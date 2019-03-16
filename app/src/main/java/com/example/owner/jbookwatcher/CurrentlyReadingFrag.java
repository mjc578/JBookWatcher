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

    public CurrentlyReadingFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View crView = inflater.inflate(R.layout.fragment_currently_reading, container, false);

        //TODO: gonna have to load books from database or whatever not this this erases it each load
        bookList = new ArrayList<Book>();
        bookAdapter = new BookListAdapter(getContext(), null);

        list = crView.findViewById(R.id.curr_read_list_view);

        setFabulousButton(crView);
        setOnListListener(crView);
        
        return crView;
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
                final AlertDialog dLog = new AlertDialog.Builder(getContext())
                        .setView(dcrView)
                        .setTitle("Enter Book Info")
                        .setPositiveButton("Done", null)
                        .setNegativeButton("Cancel", null)
                        .create();

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
                                Book book = new Book(enteredTitle);
                                //check for other fields
                                EditText etAuthor = dcrView.findViewById(R.id.edit_text_author_curr);
                                if(!etAuthor.getText().toString().equals("")){
                                    book.setAuthor(etAuthor.getText().toString());
                                }
                                EditText etPageNum = dcrView.findViewById(R.id.edit_text_book_pages_curr);
                                if(!etPageNum.getText().toString().equals("")){
                                    book.setPageNum(Integer.parseInt(etPageNum.getText().toString()));
                                }
                                EditText etStartDate = dcrView.findViewById(R.id.edit_text_curr_read_start_date);
                                if(!etStartDate.getText().toString().equals("Start Date")){
                                    book.setStartDate(etStartDate.getText().toString());
                                }
                                bookList.add(book);
                                crView.findViewById(R.id.curr_list_text_view).setVisibility(View.GONE);
                                list.setAdapter(new BookListAdapter(getContext(), bookList));

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
                String myFormat = "MM/dd/yy";
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

    private void setOnListListener(View crView){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.findViewById(R.id.selected_curr_book_buttons).getVisibility() == View.GONE){
                    view.findViewById(R.id.selected_curr_book_buttons).setVisibility(View.VISIBLE);

                    //setDeleteListener(view, position);
                    //setPlayListener(view, position);
                }
                else{
                    view.findViewById(R.id.selected_curr_book_buttons).setVisibility(View.GONE);
                }
            }
        });
    }
}