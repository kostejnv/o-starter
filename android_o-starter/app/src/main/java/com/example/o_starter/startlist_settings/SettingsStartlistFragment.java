package com.example.o_starter.startlist_settings;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;
import android.preference.Preference;
import android.preference.PreferenceDataStore;
import android.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.example.o_starter.DatabaseUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.activities.SettingsStartlistActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * Fragment for editing given competition in {@link SettingsStartlistActivity SettingsStartlistActivity}
 */
public class SettingsStartlistFragment extends PreferenceFragment {

    private static final String TAG = "SettingsStartlistFrag";
    public static final String TAG_COMPETITION_ID = "TAG_COMPETITION_ID";

    /**
     * preparing Preferences
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_startlist);

        Bundle args = getArguments();
        int competitionId = args.getInt(TAG_COMPETITION_ID);

        //add data store
        PreferenceManager preferenceManager = getPreferenceManager();
        PreferenceDataStore dataStore = new StartlistsSettingsDataStore(getActivity(),competitionId);
        preferenceManager.setPreferenceDataStore(dataStore);

        //initialize components
        SwitchPreference sendOnServerPref = (SwitchPreference) findPreference("KEY_SEND_ON_SERVER");
        sendOnServerPref.setChecked(dataStore.getBoolean("KEY_SEND_ON_SERVER", false));

        EditTextPreference changeNamePref = (EditTextPreference) findPreference("KEY_NAME");
        String name = dataStore.getString("KEY_NAME", null);
        changeNamePref.setText(name);
        changeNamePref.setSummary(name);
        changeNamePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());
                dataStore.putString("KEY_NAME", (String) newValue);
                ((DatabaseUpdateListener)getActivity()).OnDBUpdate();
                return true;
            }
        });

        //prepare collections for categories to show selecting
        Set<String> allCategoriesSet = dataStore.getStringSet("KEY_ALL_CATEGORIES", new HashSet<>());
        String[] allCategoriesArray = new String[allCategoriesSet.size()];
        allCategoriesSet.toArray(allCategoriesArray);
        Set<String> categoriesToShowSet = dataStore.getStringSet("KEY_CATEGORIES_TO_SHOW", new HashSet<>());

        //initialize categorize to show selection
        MultiSelectListPreference categoriesToSHowPref = (MultiSelectListPreference) findPreference("KEY_CATEGORIES_TO_SHOW");
        categoriesToSHowPref.setSummary(SetToString(categoriesToShowSet));
        categoriesToSHowPref.setEntries(allCategoriesArray);
        categoriesToSHowPref.setEntryValues(allCategoriesArray);
        categoriesToSHowPref.setValues(categoriesToShowSet);
        categoriesToSHowPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(SetToString((Set<String>)newValue));
                dataStore.putStringSet("KEY_CATEGORIES_TO_SHOW", (Set<String>) newValue);
                ((DatabaseUpdateListener)getActivity()).OnDBUpdate();
                return true;
            }
        });





        Log.i(TAG, "fragment created");
    }

    /**
     * Get human readable String from Set of String
     */
    private static String SetToString(Set<String> strings){
        StringBuilder builder = new StringBuilder();
        for (String string : strings){
            builder.append(string + ", ");
        }

        String output = builder.toString();

        //if Set is empty
        if(strings.size() == 0)
            return output;

        return output.substring(0, output.length()-2);
    }
}
