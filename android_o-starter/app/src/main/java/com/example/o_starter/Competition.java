package com.example.o_starter;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "competitions")
public class Competition {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(name = "start_time")
    private Date startTime;
    @Embedded
    private CompetitionSettings settings;

    @Ignore
    public Competition() {  }

    @Ignore
    public Competition(int id, String name, Date startTime, CompetitionSettings settings) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.settings = settings;
    }

    public Competition(String name, Date startTime, CompetitionSettings settings) {
        this.name = name;
        this.startTime = startTime;
        this.settings = settings;
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
}
