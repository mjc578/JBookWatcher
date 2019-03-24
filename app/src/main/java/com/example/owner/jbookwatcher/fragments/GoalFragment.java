package com.example.owner.jbookwatcher.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.jbookwatcher.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalFragment extends Fragment {

    Button goalButton;
    Button cancelButton;
    Button updateButton;
    TextView goalText;
    int goalVal;

    public GoalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fView = inflater.inflate(R.layout.fragment_goal, container, false);

        goalButton = fView.findViewById(R.id.set_goal_button);
        cancelButton = fView.findViewById(R.id.cancel_goal_button);
        updateButton = fView.findViewById(R.id.update_goal_button);
        goalText = fView.findViewById(R.id.goal_text);

        setSetListener(fView);
        setCancelListener(fView);
        setUpdateListener(fView);

        return fView;
    }

    private void setSetListener(View fView){
        fView.findViewById(R.id.set_goal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dView = View.inflate(getContext(), R.layout.set_goal_dialog,null);
                final AlertDialog dLog = new AlertDialog.Builder(getContext())
                        .setView(dView)
                        .setTitle(R.string.set_new_page_goal)
                        .setPositiveButton("Set", null)
                        .setNegativeButton("Cancel", null)
                        .create();

                dLog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                EditText eGoalPrompt = dView.findViewById(R.id.user_input_start_goal);
                                String goalInput = eGoalPrompt.getText().toString();
                                if(goalInput.equals("")){
                                    Toast.makeText(getContext(), "No value was entered", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if(Integer.parseInt(goalInput) == 0){
                                    Toast.makeText(getContext(), "Enter a non-zero value", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                goalVal = Integer.parseInt(goalInput);
                                goalText.setText(getString(R.string.goal_start_set, goalVal));
                                goalButton.setVisibility(View.GONE);
                                cancelButton.setVisibility(View.VISIBLE);
                                updateButton.setVisibility(View.VISIBLE);
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

    private void setCancelListener(View fView){
        fView.findViewById(R.id.cancel_goal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dLog = new AlertDialog.Builder(getContext());
                dLog.setTitle("Cancel Goal");
                dLog.setMessage("Are you sure you want to cancel your page goal?");
                dLog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       goalText.setText(R.string.no_goal_text);
                       cancelButton.setVisibility(View.GONE);
                       updateButton.setVisibility(View.GONE);
                       goalButton.setVisibility(View.VISIBLE);
                    }
                });
                dLog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dLog.show();
            }
        });
    }

    private void setUpdateListener(View fView){
        fView.findViewById(R.id.update_goal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dView = View.inflate(getContext(), R.layout.update_goal,null);
                final AlertDialog dLog = new AlertDialog.Builder(getContext())
                        .setView(dView)
                        .setTitle("Update Pages Read")
                        .setPositiveButton("Set", null)
                        .setNegativeButton("Cancel", null)
                        .create();

                dLog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                EditText eGoalPrompt = dView.findViewById(R.id.user_input_update_goal);
                                String goalInput = eGoalPrompt.getText().toString();
                                if(goalInput.equals("")) {
                                    Toast.makeText(getContext(), "No value was entered", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if(Integer.parseInt(goalInput) == 0){
                                    Toast.makeText(getContext(), "Enter a non-zero value", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                int x = Integer.parseInt(goalInput);
                                if(x < goalVal){
                                    goalText.setText(getString(R.string.goal_progress_set, x, goalVal));
                                }
                                else{
                                    goalText.setText(getString(R.string.goal_progress_done, x, goalVal));
                                }
                                dLog.dismiss();
                            }
                        });
                    }
                });
                dLog.show();
            }
        });
    }
}
