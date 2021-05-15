package com.example.o_starter.database.converters;

import androidx.room.TypeConverter;

import java.util.Comparator;
import java.util.Date;

/**
 * Self-documenting
 */
public class DateToLongConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
