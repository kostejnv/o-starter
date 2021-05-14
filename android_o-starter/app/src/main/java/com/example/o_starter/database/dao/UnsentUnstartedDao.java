package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.UnsentChange;
import com.example.o_starter.database.entities.UnsentUnstertedRunner;

import java.util.List;

@Dao
public interface UnsentUnstartedDao {
    @Insert
    long insertSingleRunner(UnsentUnstertedRunner runner);

    @Delete
    void deleteRunners(List<UnsentUnstertedRunner> runners);

    @Update
    void updateSigleRunner(UnsentUnstertedRunner runner);

    @Query("SELECT * FROM unsent_unstarted_runners " +
            "JOIN unstarted_runner ON unsent_unstarted_runners.unstarted_runner_id = unstarted_runner.id " +
            "WHERE unstarted_runner.competition_id = :competition_id")
    List<UnsentUnstertedRunner> GetUnsentUnstartedByCompetitionId(int competition_id);
}
