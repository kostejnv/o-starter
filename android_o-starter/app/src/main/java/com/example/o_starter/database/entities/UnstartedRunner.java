package com.example.o_starter.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

@Entity(tableName = "unstarted_runner",
        primaryKeys = {"runnerId", "competitionId"},
        foreignKeys = {@ForeignKey(entity = Runner.class, parentColumns = "id", childColumns = "runnerId"),
                        @ForeignKey(entity = Competition.class, parentColumns = "id", childColumns = "competitionId")})
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
