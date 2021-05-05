package com.example.o_starter.database;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

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
