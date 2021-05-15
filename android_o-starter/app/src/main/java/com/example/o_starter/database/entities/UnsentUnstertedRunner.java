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
        foreignKeys = {@ForeignKey(entity = UnstartedRunner.class, parentColumns = "id", childColumns = "unstarted_runner_id", onDelete = CASCADE)})
public class UnsentUnstertedRunner {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "unstarted_runner_id")
    private int unstartedRunnerId;

    public UnsentUnstertedRunner(int unstartedRunnerId) {
        this.unstartedRunnerId = unstartedRunnerId;
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

    public int getUnstartedRunnerId() {
        return unstartedRunnerId;
    }

    public void setUnstartedRunnerId(int unstartedRunnerId) {
        this.unstartedRunnerId = unstartedRunnerId;
    }
}