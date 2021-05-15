package com.example.o_starter.database.entities.abstracts;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.o_starter.database.converters.ListDateToStringConverter;

import java.util.Date;
import java.util.List;

/**
 * Abstract class for List of minutes (Date).
 *
 * Main purpose is return type for query in database {@link com.example.o_starter.database.dao.CompetitionDao#GetMinutesWithRunnerById(int) GetMinutesWithRunnerById}
 */
public class AbstractMinutesWithRunner {

    @TypeConverters(ListDateToStringConverter.class)
    @ColumnInfo(name = "minutes_with_runner")
    public List<Date> minutesWithRunner;

    public AbstractMinutesWithRunner(List<Date> minutesWithRunner) {
        this.minutesWithRunner = minutesWithRunner;
    }

    public List<Date> getMinutesWithRunner() {
        return minutesWithRunner;
    }

    public void setMinutesWithRunner(List<Date> minutesWithRunner) {
        this.minutesWithRunner = minutesWithRunner;
    }
}
