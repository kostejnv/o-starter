package com.example.o_starter.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class HashSetStringToJsonConverter {

    @TypeConverter
    public String HashSetStringToJson(HashSet<String> set){
        Gson gson = new Gson();
        return gson.toJson(set);
    }

    @TypeConverter
    public HashSet<String> JsonToHashSet(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<HashSet<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
