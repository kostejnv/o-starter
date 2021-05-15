package com.example.o_starter.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.o_starter.database.converters.ListStringToJsonConverter;

import java.util.HashSet;
import java.util.List;

/**
 * Part of Entity {@link Competition Competition} in database.
 *
 * It contains information about behavior of application for given competition
 */
public class CompetitionSettings {

    private Boolean sendOnServer;

    @TypeConverters(ListStringToJsonConverter.class)
    @ColumnInfo(name = "categories_to_show")
    private List<String> categoriesToShow;

    public CompetitionSettings(Boolean sendOnServer, List<String> categoriesToShow) {
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

    public List<String> getCategoriesToShow() {
        return categoriesToShow;
    }

    public void setCategoriesToShow(List<String> categoriesToShow) {
        this.categoriesToShow = categoriesToShow;
    }
}
