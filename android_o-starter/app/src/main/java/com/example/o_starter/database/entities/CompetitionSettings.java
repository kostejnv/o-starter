package com.example.o_starter.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.o_starter.database.converters.HashSetStringToJsonConverter;

import java.util.HashSet;

public class CompetitionSettings {

    private Boolean sendOnServer;

    @TypeConverters(HashSetStringToJsonConverter.class)
    @ColumnInfo(name = "categories_to_show")
    private HashSet<String> categoriesToShow;

    public CompetitionSettings(Boolean sendOnServer, HashSet<String> categoriesToShow) {
        this.sendOnServer = sendOnServer;
        this.categoriesToShow = categoriesToShow;
    }

    @Ignore
    public CompetitionSettings() {
    }

    public Boolean getSendOnServer() {
        return sendOnServer;
    }

    public void setSendOnServer(Boolean sendOnServer) {
        this.sendOnServer = sendOnServer;
    }

    public HashSet<String> getCategoriesToShow() {
        return categoriesToShow;
    }

    public void setCategoriesToShow(HashSet<String> categoriesToShow) {
        this.categoriesToShow = categoriesToShow;
    }
}
