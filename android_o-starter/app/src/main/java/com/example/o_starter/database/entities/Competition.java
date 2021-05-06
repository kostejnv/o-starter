package com.example.o_starter.database.entities;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.o_starter.database.converters.ArrayListDateToStringConverter;
import com.example.o_starter.database.converters.DateToLongConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


@Entity(tableName = "competitions")
public class Competition {

    @Ignore
    private static final String TAG = "CompetitionEnt";

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(name = "start_time")
    @TypeConverters(DateToLongConverter.class)
    private Date startTime;

    @ColumnInfo(name = "minutes_with_runner")
    @TypeConverters(ArrayListDateToStringConverter.class)
    private List<Date> minutesWithRunner;

    @Embedded
    private CompetitionSettings settings;
    private int change;

    @Ignore
    public Competition() {  }

    @Ignore
    public Competition(int id, String name, Date startTime, List<Date> minutesWithRunner, CompetitionSettings settings, int change) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.minutesWithRunner = minutesWithRunner;
        this.settings = settings;
        this.change = change;
    }

    public Competition(String name, Date startTime, List<Date> minutesWithRunner, CompetitionSettings settings, int change) {
        this.name = name;
        this.startTime = startTime;
        this.minutesWithRunner = minutesWithRunner;
        this.settings = settings;
        this.change = change;
    }




    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public CompetitionSettings getSettings() {
        return settings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setSettings(CompetitionSettings settings) {
        this.settings = settings;
    }

    public List<Date> getMinutesWithRunner() {
        return minutesWithRunner;
    }

    public void setMinutesWithRunner(List<Date> minutesWithRunner) {
        this.minutesWithRunner = minutesWithRunner;
    }

    @SuppressLint("SimpleDateFormat")
    @Ignore
    public void SetInfoByRunners(ArrayList<Runner> runners){
        CompetitionSettings settings = new CompetitionSettings();
        settings.setSendOnServer(false);

        HashSet<String> allCategories = new HashSet<String>();

        SortedSet<Date> minutesSet = new TreeSet<Date>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return (int) (o1.getTime()-o2.getTime());
            }
        });

        for (Runner runner : runners) {
            allCategories.add(runner.getCategory());
            minutesSet.add(runner.getStartTime());
        }
        settings.setCategoriesToShow(allCategories);
        setChange(0);
        setSettings(settings);
        setStartTime(minutesSet.first());
        setMinutesWithRunner(new ArrayList<Date>(minutesSet));
        Log.i(TAG, String.format("get all parametrs from runner to competition " + getId() + ", first minute is " + new SimpleDateFormat("hh:mm").format(minutesSet.first())));
    }
}
