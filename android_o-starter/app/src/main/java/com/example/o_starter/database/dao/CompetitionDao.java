package com.example.o_starter.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.abstracts.AbstractCategoriesToShow;
import com.example.o_starter.database.entities.abstracts.AbstractMinutesWithRunner;

import java.util.List;

/**
 * Class with methods that edit "competitions" table in database
 */
@Dao
public interface CompetitionDao {

    @Insert
    long insertSingleCompetition(Competition competition);

    @Delete
    void deleteSingleCompetition(Competition competition);

    @Update
    void updateSingleCompetition(Competition competition);

    /**
     * @return number of items in "competitions" table
     */
    @Query("SELECT COUNT(*) FROM competitions")
    int GetCompetitionCount();

    /**
     * @return all competition in table
     */
    @Query("SELECT * FROM competitions")
    List<Competition> GetAllCompetition();

    /**
     * delete one competiton from table by ID
     * @param id unique identificator
     */
    @Query("DELETE FROM competitions WHERE id = :id")
    void DeleteById(int id);

    /**
     * Return all minutes of given competition where at least one runner starts
     * @param id unique identificator of competiton
     * @return Abstract class with List<Date!
     */
    @Query("SELECT minutes_with_runner FROM competitions WHERE id = :id")
    AbstractMinutesWithRunner GetMinutesWithRunnerById(int id);

    /**
     * Return List of categories that user wants to show for given competition
     * @param id ID of competition
     * @return Abstract class with List<String!
     */
    @Query("SELECT categories_to_show FROM competitions where id = :id")
    AbstractCategoriesToShow GetCategoriesToShow(int id);

    /**
     * select competition from table by ID
     * @param id unique identificator of competition
     * @return competition with given ID
     */
    @Query("SELECT * FROM competitions WHERE id = :id")
    Competition GetCompetitionById(int id);
}
