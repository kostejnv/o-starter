package com.example.o_starter.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Entity for table "unsent_unstarted_runners" in database.
 *
 * It contains ID of unstarted_runner that was not sent to Server
 */
@Entity(tableName = "unsent_unstarted_runners",
        foreignKeys = {@ForeignKey(entity = Runner.class, parentColumns = "id", childColumns = "runner_id", onDelete = CASCADE)})
public class UnsentUnstertedRunner {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "runner_id", index = true)
    private int runnerId;

    public UnsentUnstertedRunner(int runnerId) {
        this.runnerId = runnerId;
    }

    @Ignore
    public UnsentUnstertedRunner() {
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
}