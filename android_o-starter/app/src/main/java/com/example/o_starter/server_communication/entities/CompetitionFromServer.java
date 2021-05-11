package com.example.o_starter.server_communication.entities;

import com.google.gson.Gson;

public class CompetitionFromServer {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static CompetitionFromServer getFromJSON(String json){
        CompetitionFromServer competition = new Gson().fromJson(json, CompetitionFromServer.class);
        return competition;
    }
}
