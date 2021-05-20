package com.example.o_starter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.o_starter.DatabaseUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.startlist_settings.SettingsStartlistFragment;

import java.util.Objects;

/**
 * Activity for settings given activity that runs {@link SettingsStartlistFragment SettingsStartlistFragment}
 */
public class SettingsStartlistActivity extends AppCompatActivity implements DatabaseUpdateListener {

    private static final String TAG = "SettingsStartlistAct";
    private boolean changedSomething = false;
    public static final String SHOULD_UPDATE = "SHOULD_UPDATE";

    /**
     * Run {@link SettingsStartlistFragment SettingsStartlistFragment}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_startlist);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.settings);
        Log.i(TAG, "action bar title changed");


        if(findViewById(R.id.fragment_container_layout) != null){

            if(savedInstanceState!= null){
                return;
            }

            SettingsStartlistFragment fragment = new SettingsStartlistFragment();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_container_layout, fragment).commit();
            Log.i(TAG, "setting fragment added");
        }
        Log.i(TAG, "settings activity created");
    }

    @Override
    public void OnDBUpdate() {
        changedSomething = true;
    }

    /**
     * send to calling Activity if Competition was changed
     */
    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(SHOULD_UPDATE, changedSomething);
        setResult(RESULT_OK, returnIntent);
        super.finish();


    }
}