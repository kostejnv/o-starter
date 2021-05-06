package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.o_starter.database.converters.DateToLongConverter;
import com.example.o_starter.database.entities.Runner;

import java.util.Date;
import java.util.List;

@Dao
public interface RunnerDao {

    @Insert
    long insertSingleRunner(Runner runner);

    @Delete
    void deleteSingleRunner(Runner runner);

    @Update
    void updateSiglerunner(Runner runner);

    @Query("DELETE FROM runners WHERE competition_id = :competition_id")
    void deleteByCompetitionId(int competition_id);


    @TypeConverters(DateToLongConverter.class)
    @Query("SELECT * FROM runners WHERE competition_id = :competition_id" +
                                " AND category IN (:categories_to_show)" +
                                " AND start_time = :time")
    List<Runner> GetRunnersInMinute(int competition_id, List<String> categories_to_show, Date time);
}
