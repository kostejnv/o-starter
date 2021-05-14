package com.example.o_starter.server_communication.entities;

import com.example.o_starter.EnviromentVariables;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DataToServer {

    private ArrayList<ChangeToServer> changed_runners;
    private ArrayList<UnstartedToServer> unstarted_runners;

    private String server_key = EnviromentVariables.SERVER_KEY;

    public DataToServer(ArrayList<ChangeToServer> changed_runners, ArrayList<UnstartedToServer> unstarted_runners) {
        this.changed_runners = changed_runners;
        this.unstarted_runners = unstarted_runners;
    }

    public String ToJson(){
        Gson json = new Gson();
        return json.toJson(this);
    }
}
