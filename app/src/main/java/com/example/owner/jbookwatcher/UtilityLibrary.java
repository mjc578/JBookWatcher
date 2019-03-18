package com.example.owner.jbookwatcher;

import android.widget.EditText;

public class UtilityLibrary {

    public UtilityLibrary(){}

    //Checks if given edit text has input
    public boolean editHasText(EditText et){
        return et.getText().toString().equals("");
    }




}
