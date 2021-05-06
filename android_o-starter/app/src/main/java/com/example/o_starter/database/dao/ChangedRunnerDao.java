package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.Runner;

@Dao
public interface ChangedRunnerDao {

    @Insert
    long insertSingleRunner(Runner runner);

    @Delete
    void deleteSingleRunner(Runner runner);

    @Update
    void updateSiglerunner(Runner runner);

    @Query("DELETE FROM changed_runners WHERE competition_id = :competition_id")
    void deleteByCompetitionId(int competition_id);
}
