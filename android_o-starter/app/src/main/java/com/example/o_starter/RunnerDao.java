package com.example.o_starter;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface RunnerDao {

    @Insert
    void insertSingleRunner(Runner runner);

    @Delete
    void deleteSingleRunner(Runner runner);

    @Update
    void updateSiglerunner(Runner runner);
}
