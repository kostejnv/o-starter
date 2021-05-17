package com.example.o_starter.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.o_starter.database.dao.ChangedRunnerDao;
import com.example.o_starter.database.dao.CompetitionDao;
import com.example.o_starter.database.dao.RunnerDao;
import com.example.o_starter.database.dao.UnsentChangedDao;
import com.example.o_starter.database.dao.UnsentUnstartedDao;
import com.example.o_starter.database.dao.UnstartedRunnerDao;
import com.example.o_starter.database.entities.ChangedRunner;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.database.entities.UnsentChange;
import com.example.o_starter.database.entities.UnsentUnstertedRunner;
import com.example.o_starter.database.entities.UnstartedRunner;

/**
 * Class containing Database
 *
 * In the class there is the access to datatabase throw {@link StartlistsDatabase#getInstance(Context) getInstance}
 * method and to every Dao class. Every migration is described here
 */
@Database(entities = {Competition.class, Runner.class, ChangedRunner.class, UnstartedRunner.class, UnsentChange.class, UnsentUnstertedRunner.class}, version = 1)
public abstract class StartlistsDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "startlists_db";

    //Dao classes
    public abstract CompetitionDao competitionDao();
    public abstract RunnerDao runnerDao();
    public abstract ChangedRunnerDao changedRunnerDao();
    public abstract UnstartedRunnerDao unstartedRunnerDao();
    public abstract UnsentChangedDao unsentChangedDao();
    public abstract UnsentUnstartedDao unsentUnstartedDao();

    //database instance
    private static StartlistsDatabase instance;

    /**
     * Return database if exists or create new one
     * @param context needed to cretion
     * @return database instance
     */
    public static synchronized StartlistsDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context, StartlistsDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(initialCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback initialCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
