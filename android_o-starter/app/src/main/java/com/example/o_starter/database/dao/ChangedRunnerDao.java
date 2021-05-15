package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.ChangedRunner;
import com.example.o_starter.database.entities.Runner;

/**
 * Class with methods that edit "changed_runners" table in database
 */
@Dao
public interface ChangedRunnerDao {

    @Insert
    long insertSingleRunner(ChangedRunner changedRunner);

    @Delete
    void deleteSingleRunner(ChangedRunner changedRunner);

    @Update
    void updateSiglerunner(ChangedRunner changedRunner);

    /**
     * select changedRunner from table by ID
     * @param id unique identificator of changedRunner
     * @return changeRunner with given ID
     */
    @Query("Select * FROM changed_runners WHERE id = :id")
    ChangedRunner getById(int id);
}
