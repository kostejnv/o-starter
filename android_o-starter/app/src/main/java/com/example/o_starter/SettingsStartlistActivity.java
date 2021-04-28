package com.example.o_starter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.SwitchPreference;

import android.os.Bundle;
import android.util.Log;

public class SettingsStartlistActivity extends AppCompatActivity {

    private static final String TAG = "SettingsStartlistAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_startlist);

        getSupportActionBar().setTitle(R.string.settings);
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