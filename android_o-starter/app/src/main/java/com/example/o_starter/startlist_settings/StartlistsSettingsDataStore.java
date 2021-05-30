package com.example.o_starter.startlist_settings;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceDataStore;

import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


/**
 * Class that is used for communication between database and startlist settings
 */
public class StartlistsSettingsDataStore extends PreferenceDataStore implements android.preference.PreferenceDataStore {
    public static final String KEY_SEND_ON_SERVER = "KEY_SEND_ON_SERVER";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_CATEGORIES_TO_SHOW = "KEY_CATEGORIES_TO_SHOW";
    public static final String KEY_ALL_CATEGORIES = "KEY_ALL_CATEGORIES";

    private StartlistsDatabase database;
    private int competitionId;

    public StartlistsSettingsDataStore(Context  context, int competitionId) {
        database = StartlistsDatabase.getInstance(context);
        this.competitionId = competitionId;
    }

    @Override
    public void putString(String key, @Nullable String value) {
        switch(key){
            case KEY_NAME:
                Competition competition = database.competitionDao().GetCompetitionById(competitionId);
                competition.setName(value);
                database.competitionDao().updateSingleCompetition(competition);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }

    }

    @Override
    public void putStringSet(String key, @Nullable Set<String> values) {
        Competition competition = database.competitionDao().GetCompetitionById(competitionId);

        switch(key){
            case KEY_CATEGORIES_TO_SHOW:
                competition.getSettings().setCategoriesToShow(new ArrayList<String>(values));
                database.competitionDao().updateSingleCompetition(competition);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
    }

    @Override
    public void putBoolean(String key, boolean value) {
        switch(key){
            case KEY_SEND_ON_SERVER:
                Competition competition = database.competitionDao().GetCompetitionById(competitionId);
                competition.getSettings().setSendOnServer(value);
                database.competitionDao().updateSingleCompetition(competition);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        switch(key){
            case KEY_NAME:
                return database.competitionDao().GetCompetitionById(competitionId).getName();
            default:
                return defValue;
        }
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        switch(key){
            case KEY_CATEGORIES_TO_SHOW:
                return new TreeSet<>(database.competitionDao().GetCompetitionById(competitionId).getSettings().getCategoriesToShow());
            case KEY_ALL_CATEGORIES:
                return new TreeSet<>(database.competitionDao().GetCompetitionById(competitionId).getSettings().getAllCategories());
            default:
                return defValues;
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        switch(key){
            case KEY_SEND_ON_SERVER:
                Competition competition = database.competitionDao().GetCompetitionById(competitionId);
                return competition.getSettings().getSendOnServer();
            default:
                return defValue;
        }
    }
}
