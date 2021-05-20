package com.example.o_starter.startlist_settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.o_starter.R;
import com.example.o_starter.activities.SettingsStartlistActivity;

/**
 * Fragment for editing given competition in {@link SettingsStartlistActivity SettingsStartlistActivity}
 */
public class SettingsStartlistFragment extends PreferenceFragment {

    private static final String TAG = "SettingsStartlistFrag";

    /**
     * Initialize all components
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences_startlist);
        Log.i(TAG, "fragment created");
    }
}
