package com.example.o_starter.database.entities.abstracts;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.o_starter.database.converters.ListStringToJsonConverter;

import java.util.List;

public class AbstractCategoriesToShow {

    @TypeConverters(ListStringToJsonConverter.class)
    @ColumnInfo(name = "categories_to_show")
    private List<String> categoriesToShow;

    public AbstractCategoriesToShow(List<String> categoriesToShow) {
        this.categoriesToShow = categoriesToShow;
    }

    public List<String> getCategoriesToShow() {
        return categoriesToShow;
    }

    public void setCategoriesToShow(List<String> categoriesToShow) {
        this.categoriesToShow = categoriesToShow;
    }
}
