package com.example.o_starter.server_communication.entities;

import com.example.o_starter.database.entities.ChangedRunner;
import com.google.gson.Gson;

/**
 * Class that extract data about Competition from JSON from Server
 */
public class CompetitionFromServer {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static CompetitionFromServer getFromJSON(String json){
        CompetitionFromServer competition = new Gson().fromJson(json, CompetitionFromServer.class);
        return competition;
    }
}
