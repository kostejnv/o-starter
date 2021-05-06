package com.example.o_starter.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "unstarted_runner",
        primaryKeys = {"runner_id", "competition_id"},
        foreignKeys = {@ForeignKey(entity = Runner.class, parentColumns = "id", childColumns = "runner_id", onDelete = CASCADE),
                        @ForeignKey(entity = Competition.class, parentColumns = "id", childColumns = "competition_id", onDelete = CASCADE)})
public class UnstartedRunner {

    @ColumnInfo(name = "runner_id")
    private int runnerId;
    @ColumnInfo(name = "competition_id")
    private int competitionId;

    public UnstartedRunner(int runnerId, int competitionId) {
        this.runnerId = runnerId;
        this.competitionId = competitionId;
    }

    @Ignore
    public UnstartedRunner() {
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }
}
