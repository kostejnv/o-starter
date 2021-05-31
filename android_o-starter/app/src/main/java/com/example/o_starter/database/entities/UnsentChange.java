package com.example.o_starter.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Entity for table "unsent_changes" in database.
 *
 * It contains ID of change that was not sent to Server
 */
@Entity(tableName = "unsent_changes",
        foreignKeys = {@ForeignKey(entity = ChangedRunner.class, parentColumns = "id", childColumns = "changed_runner_id", onDelete = CASCADE)})
public class UnsentChange {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "changed_runner_id", index = true)
    private int changedRunnerId;

    public UnsentChange(int changedRunnerId) {
        this.changedRunnerId = changedRunnerId;
    }

    @Ignore
    public UnsentChange() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChangedRunnerId() {
        return changedRunnerId;
    }

    public void setChangedRunnerId(int changedRunnerId) {
        this.changedRunnerId = changedRunnerId;
    }
}
