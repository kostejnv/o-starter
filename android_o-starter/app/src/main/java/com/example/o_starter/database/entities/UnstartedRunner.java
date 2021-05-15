package com.example.o_starter.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Entity for table "unstarted_runners" in database.
 *
 * It contains ID of runner that has not started till end of competition and competition ID
 */
@Entity(tableName = "unstarted_runner",
        foreignKeys = {@ForeignKey(entity = Runner.class, parentColumns = "id", childColumns = "runner_id", onDelete = CASCADE),
                        @ForeignKey(entity = Competition.class, parentColumns = "id", childColumns = "competition_id", onDelete = CASCADE)})
public class UnstartedRunner {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
