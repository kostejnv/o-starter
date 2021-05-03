package com.example.o_starter;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface CompetitionDao {

    @Insert
    void insertSingleCompetition(Competition competition);

    @Delete
    void deleteSingleCompetition(Competition competition);

    @Update
    void updateSingleCompetition(Competition competition);
}
