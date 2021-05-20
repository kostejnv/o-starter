package com.example.o_starter.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.o_starter.DatabaseUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.adapters.CompetitionsRecViewAdapter;
import com.example.o_starter.adapters.MinutesRecViewAdapter;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.server_communication.URLs;
import com.example.o_starter.startlist_settings.SettingsStartlistFragment;

import java.security.InvalidParameterException;

/**
 * Activity for checking and editing runners of give competition
 */
public class StartlistViewActivity extends AppCompatActivity implements DatabaseUpdateListener {

    public static final String COMPETITION_ID_INTENT = "COMPETITION_ID";
    public static final String TAG = "StartlistViewAct";
    private static final int REQUEST_SETTINGS_CHAGED = 101;
    private RecyclerView minuteRecView;
    private MinutesRecViewAdapter adapter;
    private int competitionId;

    /**
     * Setting all components and their functionalities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startlist_view);

        minuteRecView = findViewById(R.id.minute_rec_view);

        competitionId = getIntent().getIntExtra(COMPETITION_ID_INTENT, -1);
        if (competitionId == -1){
            Log.e(TAG, "competitionId was not send", new InvalidParameterException());
        }

        //set Minutes adapter
        adapter = new MinutesRecViewAdapter(this, competitionId);
        minuteRecView.setAdapter(adapter);
        minuteRecView.setLayoutManager(new LinearLayoutManager(this));
        minuteRecView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Log.i(TAG, "minute recycler view created");
    }

    /**
     *Create Menu for given competition
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.startlist_competition_menu, menu);
        Log.i(TAG, "option menu inflated");
        return true;
    }

    /**
     * Set menu items functionality
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.startlist_close_competition:
                AlertDialog alertDialog = new AlertDialog.Builder(StartlistViewActivity.this)
                .setTitle(getString(R.string.finish_competition))
                .setMessage(getString(R.string.really_finish_competition))
                        .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Competition competition = StartlistsDatabase.getInstance(StartlistViewActivity.this).competitionDao().GetCompetitionById(competitionId);
                        competition.FinishCompetition(StartlistViewActivity.this);
                        finish();
                    }
                }).create();
                alertDialog.show();
                return true;
            case R.id.startlist_setting_item:
                Intent intent = new Intent(this, SettingsStartlistActivity.class);
                intent.putExtra(SettingsStartlistFragment.TAG_COMPETITION_ID, competitionId);
                startActivityForResult(intent,REQUEST_SETTINGS_CHAGED);
                return true;
            case R.id.startlist_share_changes_item:
                Competition competition = StartlistsDatabase.getInstance(this).competitionDao().GetCompetitionById(competitionId);
                competition.shareChange(competitionId,this);
                return true;
            case R.id.startlist_show_changes_item:
                Intent intent1 = new Intent(this, ViewChangesActivity.class);
                intent1.putExtra(ViewChangesActivity.COMPETITION_ID_TAG, competitionId);
                this.startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Offer finish competition after press back
     */
    @Override
    public void onBackPressed() {
        Competition competition = StartlistsDatabase.getInstance(StartlistViewActivity.this).competitionDao().GetCompetitionById(competitionId);
        if (competition.isWasFinished()){
            //finish activity ordinary
            finish();
        }
        else
        {
            //offer user finish competition
            AlertDialog alertDialog = new AlertDialog.Builder(StartlistViewActivity.this)
                    .setTitle(getString(R.string.finish_competition))
                    .setMessage(R.string.back_ask)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            competition.FinishCompetition(StartlistViewActivity.this);
                        }
                    }).create();
            alertDialog.show();
        }
    }

    /**
     * Update Minutes RecView
     */
    @Override
    public void OnDBUpdate() {
        adapter.notifyDataSetChanged();
    }

    /**
     * Get information if competition was updated and call {@link StartlistViewActivity#OnDBUpdate() OnDBUpdate()}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SETTINGS_CHAGED && resultCode == RESULT_OK && data != null){
            if(data.getExtras().getBoolean(SettingsStartlistActivity.SHOULD_UPDATE)){
                OnDBUpdate();
            }
        }
    }
}