package com.example.o_starter.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Self-documenting
 */
public class ListStringToJsonConverter {

    @TypeConverter
    public String HashSetStringToJson(List<String> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public List<String> JsonToList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
