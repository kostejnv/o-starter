package com.example.o_starter;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import androidx.annotation.Nullable;

public class SettingsStartlistFragment extends PreferenceFragment {

    private static final String TAG = "SettingsStartlistFrag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences_startlist);
        Log.i(TAG, "fragment created");
    }
}
