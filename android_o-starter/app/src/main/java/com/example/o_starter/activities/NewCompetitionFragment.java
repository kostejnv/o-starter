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

import com.example.o_starter.CompetitionsUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.import_startlists.XMLv3StartlistsConverter;
import com.example.o_starter.server_communication.ServerCommunicator;

import java.util.ArrayList;

public class NewCompetitionFragment extends DialogFragment {

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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.new_competition_dialog, null,false);

        InitializeComponents(view);
        requestStoragePermission();

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Tag, "click on load");

                if(checkStoragePermission()) {
                    performFileSearch();

                }
                else{
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });


        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("New Competition")
                .setPositiveButton(R.string.create, null) //Set to null. We override the onclick
                .setNegativeButton("cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (newCompetition == null || competitionNameTextView.getText().toString().equals("")){
                            Toast.makeText(getContext(), "Invalid data", Toast.LENGTH_SHORT).show();
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
                            ((CompetitionsUpdateListener)(MainActivity)(getActivity())).OnDBUpdate();
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

    private void InitializeComponents(View view){

        competitionNameTextView = view.findViewById(R.id.competitionNameTextView);
        loadButton = view.findViewById(R.id.loadButton);
        okImageView = view.findViewById(R.id.OkImageView);
        crossImageView = view.findViewById(R.id.CrossImageView);
        sendOnServerSwitch = view.findViewById(R.id.sendOnServerSwitch);

    }

    private void requestStoragePermission(){
        if(!checkStoragePermission()){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }

    private boolean checkStoragePermission(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void performFileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/xml");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }


    //TODO: je potreba?
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){

            }
        }
    }

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

    private class ProcessXMLDataAsyncTask extends AsyncTask<Uri, Void, Void>{

        private Competition competition;
        private ArrayList<Runner> runners;
        private boolean WasProcessOK;


        @Override
        protected Void doInBackground(Uri... uris) {
            Uri uri = uris[0];
            XMLv3StartlistsConverter converter = new XMLv3StartlistsConverter(uri, getContext());
            try {
                competition = converter.getCompetition();
                runners = converter.getRunners();
                competition.SetInfoByRunners(runners);
                WasProcessOK = true;
            } catch (FormatException e) {
                WasProcessOK = false;
            }

            return null;
        }

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

    private class AddCompetitionToDatabase extends AsyncTask<Void,Void,Void>{

        private Competition competition;
        private ArrayList<Runner> runners;

        public AddCompetitionToDatabase(Competition competition, ArrayList<Runner> runners) {
            this.competition = competition;
            this.runners = runners;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            StartlistsDatabase db = StartlistsDatabase.getInstance(getContext());
            int competitionId = (int) db.competitionDao().insertSingleCompetition(competition);
            for(Runner runner: runners){
                runner.setCompetitionId(competitionId);
                db.runnerDao().insertSingleRunner(runner);
            }

            return null;
        }
    }

    private class AddCompetitonToServerAsyncTask extends AsyncTask<Void, Void, Void>{

        private int competitionId;
        private boolean wasSuccessful;

        public AddCompetitonToServerAsyncTask(int competitionId) {
            this.competitionId = competitionId;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            wasSuccessful = ServerCommunicator.getInstance(getContext()).CreateRaceOnServer(competitionId);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!wasSuccessful){
                Toast.makeText(getContext(), "Server connection failed. \nTry to check internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
}