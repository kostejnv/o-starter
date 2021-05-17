package com.example.o_starter.server_communication.entities;

import com.example.o_starter.database.entities.Runner;
import com.google.gson.Gson;

/**
 * Entity class of unstarted {@link Runner Runner} in format which is compatible with Server
 */
public class UnstartedToServer {

    private String given;
    private String family;
    private long start_time;
    private String club_short;
    private int card_number;
    private int start_number;
    private String registration_id;
    private String category;

    public UnstartedToServer(Runner runner){
        this.given = (runner.getName()==null) ? ""  : runner.getName();
        this.family = (runner.getSurname()==null) ? ""  : runner.getSurname();
        this.start_time = runner.getStartTime().getTime();
        this.club_short = (runner.getClubShort()==null) ? ""  : runner.getClubShort();
        this.card_number = runner.getCardNumber();
        this.start_number = runner.getStartNumber();
        this.registration_id = (runner.getRegistrationId()==null) ? ""  : runner.getRegistrationId();
        this.category = (runner.getCategory()==null) ? ""  : runner.getCategory();
    }

    public String ToJson(){
        Gson json = new Gson();
        return json.toJson(this);
    }
}
