package com.example.o_starter.server_communication.entities;

import com.example.o_starter.database.entities.Competition;
import com.google.gson.Gson;

import java.util.Date;

public class CompetitionToServer {
    private String name;
    private Date start_time;

    public CompetitionToServer(Competition competition){
        this.name = competition.getName();
        this.start_time = competition.getStartTime();
    }

    public String ToJson(){
        Gson json = new Gson();
        return json.toJson(this);
    }
}
