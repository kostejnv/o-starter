package com.example.o_starter.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Self-documenting
 */
public class ListDateToStringConverter {

    @TypeConverter
    public String ListDateToJson(List<Date> set){
        Gson gson = new Gson();
        return gson.toJson(set);
    }

    @TypeConverter
    public List<Date> JsonToListDate(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Date>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
