package com.example.o_starter.database.entities.abstracts;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.o_starter.database.converters.ArrayListDateToStringConverter;

import java.util.Date;
import java.util.List;

public class AbstractMinutesWithRunner {

    @TypeConverters(ArrayListDateToStringConverter.class)
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
