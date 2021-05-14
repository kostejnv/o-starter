package com.example.o_starter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.o_starter.R;

import java.util.Objects;

/**
 * Activity for settings given activity that runs {@link SettingsStartlistFragment SettingsStartlistFragment}
 */
public class SettingsStartlistActivity extends AppCompatActivity {

    private static final String TAG = "SettingsStartlistAct";

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

            getFragmentManager().beginTransaction().add(R.id.fragment_container_layout, new SettingsStartlistFragment()).commit();
            Log.i(TAG, "setting fragment added");
        }
        Log.i(TAG, "settings activity created");
    }
}