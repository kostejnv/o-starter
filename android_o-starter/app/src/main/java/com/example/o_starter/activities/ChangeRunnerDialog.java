package com.example.o_starter.activities;

import android.app.Dialog;
import android.content.Context;
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

import com.example.o_starter.R;
import com.example.o_starter.adapters.RunnerRecViewAdapter;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.ChangedRunner;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.database.entities.UnsentChange;
import com.example.o_starter.server_communication.ServerCommunicator;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.Objects;


/**
 * Dialog class for editing runners.
 *
 * This class cares about editing runner. It shows dialog and then
 * check if there are changes. If yes, it saves changedRunner to database
 * and try to send change on server
 */
public class ChangeRunnerDialog extends DialogFragment {

    private EditText givenNameEditText;
    private EditText familyNameEditText;
    private EditText SIEditTextNumber;
    private EditText startNumberEditTextNumber;
    private EditText regEditText;

    //calling adapter for notify purpose
    private final RunnerRecViewAdapter adapter;

    private final Runner runner;
    private ChangedRunner changedRunner;

    private static final String TAG = "ChangeDialog";

    /**
     * Class constructor
     * @param adapter calling adapter
     * @param runner editable runner
     */
    public ChangeRunnerDialog(RunnerRecViewAdapter adapter, Runner runner) {
        this.adapter = adapter;
        this.runner = runner;
    }


    /**
     * Create and set dialog functions.
     *
     * Main method of the class. Sets all components and functions.
     * It initializes all components and saves change to database
     * and sends it on server.
     *
     * @return edit runner dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        //inflate dialog view
        View view = requireActivity().getLayoutInflater().inflate(R.layout.change_runner_dialog, null, false);

        InitializeComponents(view);

        //set edittexts hints
        givenNameEditText.setHint(runner.getName());
        familyNameEditText.setHint(runner.getSurname());
        SIEditTextNumber.setHint(Integer.valueOf(runner.getCardNumber()).toString());
        startNumberEditTextNumber.setHint(Integer.valueOf(runner.getStartNumber()).toString());
        regEditText.setHint(runner.getRegistrationId());

        //set main attributes of dialog
        final AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle(R.string.change_runner)
                .setPositiveButton(R.string.change, null) //Set to null. We override the onclick
                .setNegativeButton(R.string.cancel, null)
                .create();


        //set function of "change" button
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (WasChanged()) {
                            String readableChange = getStringChange();
                            int changedRunnerId = insertChangedRunnerToDatabase();
                            updateRunnerInDatabase(runner);
                            adapter.notifyDataSetChanged();
                            //TODO:check if should send
                            SendChangeToServerAsyncTask sendChangeToServer = new SendChangeToServerAsyncTask(changedRunnerId, adapter.getContext(), runner);
                            sendChangeToServer.execute();

                            //offer user cancel change
                            Snackbar.make(requireActivity().findViewById(R.id.minute_rec_view), readableChange, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // it stops sending on server
                                            //TODO: check ifsendonserver
                                            sendChangeToServer.wasUndoPressed = true;
                                            //return change back in database
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

    /**
     * create change runner and save into database
     * @return id of changedRunner
     */
    private int insertChangedRunnerToDatabase(){
        changedRunner = new ChangedRunner(runner);
        Competition competition = StartlistsDatabase.getInstance(getContext()).competitionDao().GetCompetitionById(runner.getCompetitionId());
        //TODO: delete changes
        StartlistsDatabase.getInstance(getContext()).competitionDao().updateSingleCompetition(competition);
        return (int) StartlistsDatabase.getInstance(getContext()).changedRunnerDao().insertSingleRunner(changedRunner);
    }

    /**
     * Update changed field of runner
     */
    private void updateRunnerInDatabase(Runner runner){
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

    /**
     * check if an editView was filled
     */
    private boolean WasChanged() {
        return !givenNameEditText.getText().toString().equals("")
                || !familyNameEditText.getText().toString().equals("")
                || !SIEditTextNumber.getText().toString().equals("")
                || !startNumberEditTextNumber.getText().toString().equals("")
                ||!regEditText.getText().toString().equals("");
    }

    /**
     * create string that described change
     * @return change in read-form
     */
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

    /**
     * Self-documenting
     */
    private void InitializeComponents(View view) {
        givenNameEditText = view.findViewById(R.id.givenNameEditText);
        familyNameEditText = view.findViewById(R.id.familyNameEditText);
        SIEditTextNumber = view.findViewById(R.id.SIEditTextNumber);
        startNumberEditTextNumber = view.findViewById(R.id.startNumberEditTextNumber);
        regEditText = view.findViewById(R.id.regEditText);

    }


    /**
     * Async task for sending change on server
     */
    private static class SendChangeToServerAsyncTask extends AsyncTask<Void,Void,Void> {
        //if user wants to cancel change
        public boolean wasUndoPressed = false;
        private final int changedRunnerId;
        private boolean failedConnection = true;
        private final WeakReference<Context> contextReference;
        private final Runner runner;

        /**
         * Task constructor
         */
        private SendChangeToServerAsyncTask(int changedRunnerId, Context context, Runner runner) {
            this.changedRunnerId = changedRunnerId;
            this.contextReference = new WeakReference<Context>(context);
            this.runner = runner;
        }

        /**
         * Try to send change on Server.
         *
         * If user not cancel sending on server, add change into UnsentChanges
         * and call for Sending all data to Server
         */
        @Override
        protected Void doInBackground(Void... voids) {
            //time to pressed undo
            int SLEEP_TIME = 5000;
            SystemClock.sleep(SLEEP_TIME);
            if (!wasUndoPressed){
                // get a reference to the context if it is still there
                Context context = contextReference.get();
                if (context == null) return null;
                StartlistsDatabase.getInstance(contextReference.get()).unsentChangedDao().insertSingleChange(new UnsentChange(changedRunnerId));
                if(ServerCommunicator.getInstance(contextReference.get()).SendDataToServer(runner.getCompetitionId())){
                    Log.i(TAG, "Data sent to server");
                    failedConnection = false;
                }
                else
                {
                    Log.i(TAG, "connection error");
                    failedConnection = true;
                }
            }

            return null;
        }

        /**
         * Notify user if sending to Server failed
         */
        @Override
        protected void onPostExecute(Void aVoid) {

            // get a reference to the context if it is still there
            Context context = contextReference.get();
            if (context == null) return;

            if (failedConnection){
                Toast.makeText(context, "Server connection failed. \nTry to check internet connection", Toast.LENGTH_LONG).show();
            }
        }
    }
}
