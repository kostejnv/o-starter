package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.database.entities.UnstartedRunner;

/**
 * Class with methods that edit "unstarted_runners" table in database
 */
@Dao
public interface UnstartedRunnerDao {

    @Insert
    long insertSingleRunner(UnstartedRunner runner);

    @Delete
    void deleteSingleRunner(UnstartedRunner runner);

    @Update
    void updateSiglerunner(UnstartedRunner runner);

    /**
     * select unstarted_runner from table by ID
     * @param id unique identificator of unstarted_runner
     * @return unstarted_runner with given ID
     */
    @Query("SELECT * FROM unstarted_runner WHERE id = :id")
    UnstartedRunner GetById(int id);
}
