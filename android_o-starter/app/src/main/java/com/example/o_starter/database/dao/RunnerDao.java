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

/**
 * Class with methods that edit "runners" table in database
 */
@Dao
public interface RunnerDao {

    @Insert
    long insertSingleRunner(Runner runner);

    @Delete
    void deleteSingleRunner(Runner runner);

    @Update
    void updateSiglerunner(Runner runner);

    /**
     * Returns runners of given competition that start in specific minute
     * @param competition_id ID of runners competition
     * @param categories_to_show categories that are allowed for returned runners
     * @param time time of start returned runners
     * @return List of runners satisfied given conditions
     */
    @TypeConverters(DateToLongConverter.class)
    @Query("SELECT * FROM runners WHERE competition_id = :competition_id" +
                                " AND category IN (:categories_to_show)" +
                                " AND start_time = :time")
    List<Runner> GetRunnersInMinute(int competition_id, List<String> categories_to_show, Date time);

    /**
     * select runenr from table by ID
     * @param id unique identificator of runner
     * @return runner with given ID
     */
    @Query("SELECT * FROM runners WHERE id=:id")
    Runner getById(int id);

    /**
     * return runners of given competition that have not been checked yet
     */
    @Query("SELECT * FROM runners WHERE competition_id = :competitionId AND NOT checked")
    List<Runner> GetUnstartedRunners(int competitionId);
}
