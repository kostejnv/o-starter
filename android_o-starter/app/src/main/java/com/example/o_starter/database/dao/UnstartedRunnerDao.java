package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.database.entities.UnstartedRunner;

@Dao
public interface UnstartedRunnerDao {

    @Insert
    long insertSingleRunner(UnstartedRunner runner);

    @Delete
    void deleteSingleRunner(UnstartedRunner runner);

    @Update
    void updateSiglerunner(UnstartedRunner runner);

    @Query("DELETE FROM unstarted_runner WHERE competition_id = :competition_id")
    void deleteByCompetitionId(int competition_id);

    @Query("SELECT * FROM unstarted_runner WHERE id = :id")
    UnstartedRunner GetById(int id);
}
