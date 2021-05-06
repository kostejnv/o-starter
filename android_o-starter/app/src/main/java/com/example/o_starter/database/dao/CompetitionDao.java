package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.abstracts.AbstractMinutesWithRunner;

import java.util.List;

@Dao
public interface CompetitionDao {

    @Insert
    long insertSingleCompetition(Competition competition);

    @Delete
    void deleteSingleCompetition(Competition competition);

    @Update
    void updateSingleCompetition(Competition competition);

    @Query("SELECT COUNT(*) FROM competitions")
    int GetCompetitionCount();

    @Query("SELECT * FROM competitions")
    List<Competition> GetAllCompetition();

    @Query("DELETE FROM competitions WHERE id = :id")
    void DeleteById(int id);


    @Query("SELECT minutes_with_runner FROM competitions WHERE id = :id")
    AbstractMinutesWithRunner GetMinutesWithRunnerById(int id);
}
