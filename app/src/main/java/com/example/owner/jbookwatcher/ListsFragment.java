package com.example.owner.jbookwatcher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListsFragment extends Fragment {


    public ListsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lfView = inflater.inflate(R.layout.fragment_lists, container, false);

        setCurrReadListener(lfView);
        setFinishedReadListener(lfView);
        setToReadListener(lfView);

        return lfView;
    }

    private void setCurrReadListener(View lfView){
        lfView.findViewById(R.id.LL_curr_reading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currReadFrag = new CurrentlyReadingFrag();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.lists_container, currReadFrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void setFinishedReadListener(View lfView){
        lfView.findViewById(R.id.LL_finished_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment finishedReadingFrag = new FinishedReadingFrag();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.lists_container, finishedReadingFrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void setToReadListener(View lfView){
        lfView.findViewById(R.id.LL_to_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment toReadFrag = new ToReadFrag();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.lists_container, toReadFrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

}
