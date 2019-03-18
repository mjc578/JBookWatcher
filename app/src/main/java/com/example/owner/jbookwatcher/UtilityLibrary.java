package com.example.owner.jbookwatcher;

import android.widget.EditText;

public class UtilityLibrary {

    public UtilityLibrary(){}

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



}
