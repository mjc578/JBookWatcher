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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

    public CurrentlyReadingFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View crView = inflater.inflate(R.layout.fragment_currently_reading, container, false);
        setFabulousButton(crView);
        return crView;
    }

    private void setFabulousButton(final View crView){
        crView.findViewById(R.id.fab_curr_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dcrView = View.inflate(getContext(), R.layout.currently_reading_dialog,null);
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
                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                TextView entTextTV = dcrView.findViewById(R.id.edit_text_book_title_curr);
                                String enteredTitle = entTextTV.getText().toString();
                                //TODO need to consider the other book fields...
                                if(enteredTitle.equals("")){
                                    Toast.makeText(getContext(), "No book title entered", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Book book = new Book(enteredTitle);
                                //TODO add other fields here

                                bookList.add(book);
                                crView.findViewById(R.id.curr_list_text_view).setVisibility(View.GONE);

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

    private void datePicker(final View dcrView){
        final Calendar myCalendar = Calendar.getInstance();

        final EditText dateButton = dcrView.findViewById(R.id.edit_text_curr_read_start_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateButton.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

}
