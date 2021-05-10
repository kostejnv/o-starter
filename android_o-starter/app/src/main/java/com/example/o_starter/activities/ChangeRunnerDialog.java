package com.example.o_starter.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.o_starter.DialogFragUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.adapters.RunnerRecViewAdapter;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.ChangedRunner;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;
import com.google.android.material.snackbar.Snackbar;

public class ChangeRunnerDialog extends DialogFragment {

    private EditText givenNameEditText;
    private EditText familyNameEditText;
    private EditText SIEditTextNumber;
    private EditText startNumberEditTextNumber;
    private EditText regEditText;

    private RunnerRecViewAdapter adapter;

    private Runner runner;
    private ChangedRunner changedRunner;

    private static final String TAG = "ChangeDialog";

    public ChangeRunnerDialog(RunnerRecViewAdapter adapter, Runner runner) {
        this.adapter = adapter;
        this.runner = runner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.change_runner_dialog, null, false);

        InitializeComponents(view);

        givenNameEditText.setHint(runner.getName());
        familyNameEditText.setHint(runner.getSurname());
        SIEditTextNumber.setHint(new Integer(runner.getCardNumber()).toString());
        startNumberEditTextNumber.setHint(new Integer(runner.getStartNumber()).toString());
        regEditText.setHint(runner.getRegistrationId());

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Change Runner")
                .setPositiveButton("Change", null) //Set to null. We override the onclick
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (WasChanged()) {
                            String change = getStringChange();
                            insertChangedRunnerToDatabase();
                            updateRunnerInDatabase();
                            adapter.notifyDataSetChanged();
                            SendChangeToServerAsyncTask sendChangeToServer = new SendChangeToServerAsyncTask();
                            sendChangeToServer.execute();
                            Snackbar.make(getActivity().findViewById(R.id.minute_rec_view), change, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            sendChangeToServer.wasUndoPressed = true;
                                            runner.changeByChangedRunner(changedRunner);
                                            StartlistsDatabase.getInstance(getContext()).runnerDao().updateSiglerunner(runner);
                                            StartlistsDatabase.getInstance(getContext()).changedRunnerDao().deleteSingleRunner(changedRunner);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                            .show();
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Nothing Changed", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }
                });
            }
        });

        return dialog;

    }

    private void insertChangedRunnerToDatabase(){
        changedRunner = new ChangedRunner(runner);
        Competition competition = StartlistsDatabase.getInstance(getContext()).competitionDao().GetCompetitionById(runner.getCompetitionId());
        changedRunner.setChange(competition.getChange());
        competition.setChange(competition.getChange()+1);
        StartlistsDatabase.getInstance(getContext()).competitionDao().updateSingleCompetition(competition);
        StartlistsDatabase.getInstance(getContext()).changedRunnerDao().insertSingleRunner(changedRunner);
    }

    private void updateRunnerInDatabase(){
        if(!givenNameEditText.getText().toString().equals("")) {
            runner.setName(givenNameEditText.getText().toString());
        }
        if(!familyNameEditText.getText().toString().equals("")) {
            runner.setSurname(familyNameEditText.getText().toString());
        }
        if (!SIEditTextNumber.getText().toString().equals("")){
            runner.setCardNumber(Integer.parseInt(SIEditTextNumber.getText().toString()));
        }
        if (!startNumberEditTextNumber.getText().toString().equals("")){
            runner.setStartNumber(Integer.parseInt(startNumberEditTextNumber.getText().toString()));
        }
        if(!regEditText.getText().toString().equals("")) {
            runner.setRegistrationId(regEditText.getText().toString());
        }
        StartlistsDatabase.getInstance(getContext()).runnerDao().updateSiglerunner(runner);
    }

    private boolean WasChanged() {
        return !givenNameEditText.getText().toString().equals("")
                || !familyNameEditText.getText().toString().equals("")
                || !SIEditTextNumber.getText().toString().equals("")
                || !startNumberEditTextNumber.getText().toString().equals("")
                ||!regEditText.getText().toString().equals("");
    }

    private String getStringChange() {
        StringBuilder stringChange = new StringBuilder();
        if (!givenNameEditText.getText().toString().equals("")) {
            stringChange.append(runner.getName());
            stringChange.append(" -> ");
            stringChange.append(givenNameEditText.getText().toString());
            stringChange.append("\n");
        }
        if (!familyNameEditText.getText().toString().equals("")) {
            stringChange.append(runner.getSurname());
            stringChange.append(" -> ");
            stringChange.append(familyNameEditText.getText().toString());
            stringChange.append("\n");
        }
        if (!SIEditTextNumber.getText().toString().equals("")) {
            stringChange.append(runner.getCardNumber());
            stringChange.append(" -> ");
            stringChange.append(SIEditTextNumber.getText().toString());
            stringChange.append("\n");
        }
        if (!startNumberEditTextNumber.getText().toString().equals("")) {
            stringChange.append(runner.getStartNumber());
            stringChange.append(" -> ");
            stringChange.append(startNumberEditTextNumber.getText().toString());
            stringChange.append("\n");
        }
        if (!regEditText.getText().toString().equals("")) {
            stringChange.append(runner.getRegistrationId());
            stringChange.append(" -> ");
            stringChange.append(regEditText.getText().toString());
            stringChange.append("\n");
        }
        String change = stringChange.toString();
        return change.substring(0, change.length()-1);
    }

    private void InitializeComponents(View view) {
        givenNameEditText = view.findViewById(R.id.givenNameEditText);
        familyNameEditText = view.findViewById(R.id.familyNameEditText);
        SIEditTextNumber = view.findViewById(R.id.SIEditTextNumber);
        startNumberEditTextNumber = view.findViewById(R.id.startNumberEditTextNumber);
        regEditText = view.findViewById(R.id.regEditText);

    }



    private class SendChangeToServerAsyncTask extends AsyncTask<Void,Void,Void> {
        public boolean wasUndoPressed = false;
        private static final int SLEEP_TIME = 5000;

        @Override
        protected Void doInBackground(Void... voids) {
            SystemClock.sleep(SLEEP_TIME);
            if (!wasUndoPressed){
                //TODO: poslat zmenu na server
                Log.i(TAG, "Sent to server");
            }
            else
            {
                Log.i(TAG, "Not Sent to server");
            }

            return null;
        }
    }
}
