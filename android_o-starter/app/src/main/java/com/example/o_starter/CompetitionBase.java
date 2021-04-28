package com.example.o_starter;

import java.util.ArrayList;

public class CompetitionBase {

    private String name;
    private String date;
    private ArrayList<CompetitiorsBase> competitors = new ArrayList<CompetitiorsBase>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<CompetitiorsBase> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(ArrayList<CompetitiorsBase> competitors) {
        this.competitors = competitors;
    }

    public CompetitionBase(String name, String date, ArrayList<CompetitiorsBase> competitors) {
        this.name = name;
        this.date = date;
        this.competitors = competitors;
    }
}
