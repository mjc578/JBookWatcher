package com.example.owner.jbookwatcher;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateLibrary {

    public DateLibrary(){}

    //Checks if given edit text has input
    public boolean editHasText(EditText et){
        return !et.getText().toString().equals("");
    }

    //returns false if there is an inconsistency
    public boolean checkDates(EditText set, EditText fet){
        if(!editHasText(set) || !editHasText(fet)){
            return false;
        }
        //TODO: do this with calendar instances instead since comparing years this way
        //TODO: can easily lead to inconsistencies in my inconsistency checker! BAD
        String[] sDate = set.getText().toString().split("/");
        String[] fDate = fet.getText().toString().split("/");
        if(Integer.parseInt(sDate[2]) <= Integer.parseInt(fDate[2])){
            if(Integer.parseInt(sDate[1]) <= Integer.parseInt(fDate[1])){
                return Integer.parseInt(sDate[0]) > Integer.parseInt(fDate[0]);
            }
        }
        return true;
    }

    public void datePicker(final EditText et) {
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                et.setText(sdf.format(myCalendar.getTime()));
            }

        };

        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(et.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}
