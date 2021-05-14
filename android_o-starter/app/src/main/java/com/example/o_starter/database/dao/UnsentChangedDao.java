package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.database.entities.UnsentChange;

import java.util.List;

@Dao
public interface UnsentChangedDao {


    @Insert
    long insertSingleChange(UnsentChange change);

    @Delete
    void deleteChanges(List<UnsentChange> changes);

    @Update
    void updateSigleChange(UnsentChange change);

    @Query("SELECT * FROM unsent_changes " +
            "JOIN changed_runners ON unsent_changes.changed_runner_id = changed_runners.id " +
            "WHERE changed_runners.competition_id = :competition_id")
    List<UnsentChange> GetUnsentChangesByCompetitionId(int competition_id);
}
