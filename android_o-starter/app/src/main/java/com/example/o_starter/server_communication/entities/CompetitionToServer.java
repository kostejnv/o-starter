package com.example.o_starter.server_communication.entities;

import com.example.o_starter.EnviromentVariables;
import com.example.o_starter.database.entities.ChangedRunner;
import com.example.o_starter.database.entities.Competition;
import com.google.gson.Gson;

import java.util.Date;

/**
 * Entity class of {@link Competition Competition} in format which is compatible with Server
 */
public class CompetitionToServer {
    private String name;
    private Date start_time;
    private String server_key = EnviromentVariables.SERVER_KEY;

    public CompetitionToServer(Competition competition){
        this.name = competition.getName();
        this.start_time = competition.getStartTime();
    }

    public String ToJson(){
        Gson json = new Gson();
        return json.toJson(this);
    }
}
