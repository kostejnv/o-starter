package com.example.o_starter;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Competition.class, Runner.class}, version = 1)
public abstract class StartlistsDatabase extends RoomDatabase {

    //Dao classes
    public abstract CompetitionDao competitionDao();
    public abstract RunnerDao runnerDao();

    //database instance
    private static StartlistsDatabase instance;

    public static synchronized StartlistsDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context, StartlistsDatabase.class, "startlists_db")
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
