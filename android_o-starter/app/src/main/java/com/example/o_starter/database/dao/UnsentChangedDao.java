package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.database.entities.UnsentChange;

import java.util.List;

/**
 * Class with methods that edit "unsent_changed_runners" table in database
 */
@Dao
public interface UnsentChangedDao {


    @Insert
    long insertSingleChange(UnsentChange change);

    @Delete
    void deleteChanges(List<UnsentChange> changes);

    @Update
    void updateSigleChange(UnsentChange change);

    /**
     * Returns all unsent changed runner for given competition
     * @param competition_id ID of given competition
     */
    @Query("SELECT * FROM unsent_changes " +
            "JOIN changed_runners ON unsent_changes.changed_runner_id = changed_runners.id " +
            "WHERE changed_runners.competition_id = :competition_id")
    List<UnsentChange> GetUnsentChangesByCompetitionId(int competition_id);
}
