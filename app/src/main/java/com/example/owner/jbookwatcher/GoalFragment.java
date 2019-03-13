package com.example.owner.jbookwatcher;


import android.app.AlertDialog;
import android.app.Dialog;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalFragment extends Fragment {

    Button goalButton;
    Button cancelButton;
    Button updateButton;
    TextView goalText;

    public GoalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fView = inflater.inflate(R.layout.fragment_goal, container, false);
        setSetListener(fView);

        goalButton = fView.findViewById(R.id.set_goal_button);
        cancelButton = fView.findViewById(R.id.cancel_goal_button);
        updateButton = fView.findViewById(R.id.update_goal_button);
        goalText = fView.findViewById(R.id.goal_text);


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
                                int goalVal = Integer.parseInt(goalInput);
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

}
