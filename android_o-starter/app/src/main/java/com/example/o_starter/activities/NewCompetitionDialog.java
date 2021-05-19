package com.example.o_starter.activities;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.FormatException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.example.o_starter.DatabaseUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.import_startlists.XMLv3StartlistsConverter;
import com.example.o_starter.server_communication.ServerCommunicator;

import java.util.ArrayList;

/**
 * Class with dialog for creating new competition in {@link MainActivity MainActivity}
 */
public class NewCompetitionDialog extends DialogFragment {

    private static final String Tag = "NewCompFrag";
    private static final int REQUEST_STORAGE_PERMISSION = 101;
    private static final int READ_REQUEST_CODE = 1001;

    private EditText competitionNameTextView;
    private Button loadButton;
    private ImageView okImageView;
    private ImageView crossImageView;
    private Switch sendOnServerSwitch;

    private Competition newCompetition;
    private ArrayList<Runner> competitors;

    /**
     * Main method for creating and setting new competition dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        View view = requireActivity().getLayoutInflater().inflate(R.layout.new_competition_dialog, null,false);

        InitializeComponents(view);
        requestStoragePermission();

        //open file manager if click on load and permitted
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Tag, "click on load");

                if(checkStoragePermission()) {
                    performFileSearch();

                }
                else{
                    Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //create dialog and set main information
        final AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle(R.string.new_competition)
                .setPositiveButton(R.string.create, null) //Set to null. We override the onclick
                .setNegativeButton(R.string.cancel, null)
                .create();

        //override create button functions
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (newCompetition == null || competitionNameTextView.getText().toString().equals("")){
                            Toast.makeText(getContext(), R.string.invalid_data, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            newCompetition.setName(competitionNameTextView.getText().toString());
                            newCompetition.getSettings().setSendOnServer(sendOnServerSwitch.isChecked());
                            StartlistsDatabase db = StartlistsDatabase.getInstance(getContext());
                            int competitionId = (int) db.competitionDao().insertSingleCompetition(newCompetition);
                            for(Runner runner: competitors){
                                runner.setCompetitionId(competitionId);
                                db.runnerDao().insertSingleRunner(runner);
                            }
                            ((DatabaseUpdateListener)(MainActivity)(requireActivity())).OnDBUpdate();
                            if (newCompetition.getSettings().getSendOnServer()){
                                AddCompetitonToServerAsyncTask addCompetitionToServer = new AddCompetitonToServerAsyncTask(competitionId);
                                addCompetitionToServer.execute();
                            }
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        return dialog;

    }

    /**
     *Self-documenting
     */
    private void InitializeComponents(View view){

        competitionNameTextView = view.findViewById(R.id.competitionNameTextView);
        loadButton = view.findViewById(R.id.loadButton);
        okImageView = view.findViewById(R.id.OkImageView);
        crossImageView = view.findViewById(R.id.CrossImageView);
        sendOnServerSwitch = view.findViewById(R.id.sendOnServerSwitch);

    }

    /**
     * Ask for permission if it was done till now
     */
    private void requestStoragePermission(){
        if(!checkStoragePermission()){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }

    /**
     * Self-documenting
     */
    private boolean checkStoragePermission(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Open file Manager to find XML
     */
    private void performFileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/xml");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    /**
     * Get XML path and process it
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                ProcessXMLDataAsyncTask xmlProcess = new ProcessXMLDataAsyncTask();
                xmlProcess.execute(uri);
            }
        }
    }


    //__________________________________________________________________________________
    //Async Tasks

    /**
     * Async task for getting competition data from XML
     */
    private class ProcessXMLDataAsyncTask extends AsyncTask<Uri, Void, Void>{

        private Competition competition;
        private ArrayList<Runner> runners;
        private boolean WasProcessOK;


        /**
         * Process XML and get data to data fields
         */
        @Override
        protected Void doInBackground(Uri... uris) {
            Uri uri = uris[0];
            XMLv3StartlistsConverter converter = new XMLv3StartlistsConverter(uri, getContext());
            try {
                competition = converter.getCompetition();
                runners = converter.getRunners();
                WasProcessOK = true;
            } catch (FormatException e) {
                WasProcessOK = false;
            }

            return null;
        }


        /**
         *Save competition data to class and sets views
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            if(WasProcessOK){
                crossImageView.setVisibility(View.GONE);
                okImageView.setVisibility(View.VISIBLE);
                String textintextview = competitionNameTextView.getText().toString();
                newCompetition = competition;
                competitors = runners;
                if(textintextview.equals("")){
                    competitionNameTextView.setText(competition.getName());
                }
            } else{
                okImageView.setVisibility(View.GONE);
                crossImageView.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * Self-documenting
     */
    private class AddCompetitonToServerAsyncTask extends AsyncTask<Void, Void, Void>{

        private final int competitionId;
        private boolean wasSuccessful;


        public AddCompetitonToServerAsyncTask(int competitionId) {
            this.competitionId = competitionId;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            wasSuccessful = ServerCommunicator.getInstance(getContext()).CreateRaceOnServer(competitionId);

            return null;
        }


        /**
         * Show message if connection was not successful
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            if (!wasSuccessful){
                Toast.makeText(getContext(), R.string.connection_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}