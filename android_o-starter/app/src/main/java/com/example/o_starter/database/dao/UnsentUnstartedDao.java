package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.database.entities.UnsentChange;
import com.example.o_starter.database.entities.UnsentUnstertedRunner;

import java.util.List;

/**
 * Class with methods that edit "unsent_unstarted_runners" table in database
 */
@Dao
public interface UnsentUnstartedDao {
    @Insert
    long insertSingleRunner(UnsentUnstertedRunner runner);

    @Delete
    void deleteRunners(List<UnsentUnstertedRunner> runners);

    @Update
    void updateSigleRunner(UnsentUnstertedRunner runner);

    /**
     * Returns all unsent unstarted runners for given competition
     * @param competition_id ID of given competition
     */
    @Query("SELECT * FROM unsent_unstarted_runners " +
            "JOIN runners ON unsent_unstarted_runners.runner_id = runners.id " +
            "WHERE runners.competition_id = :competition_id")
    List<UnsentUnstertedRunner> GetUnsentUnstartedByCompetitionId(int competition_id);

    @Query("DELETE FROM unsent_unstarted_runners")
    void DeleteAllUnstarted();
}
