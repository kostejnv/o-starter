package com.example.o_starter.server_communication.entities;


import com.example.o_starter.EnviromentVariables;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.ChangedRunner;
import com.example.o_starter.database.entities.Runner;
import com.google.gson.Gson;

import java.util.Date;

public class ChangeToServer {

    private String old_given;
    private String old_family;
    private long old_start_time;
    private String old_club_short;
    private int old_card_number;
    private int old_start_number;
    private String old_registration_id;
    private String old_category;

    private String new_given;
    private String new_family;
    private long new_start_time;
    private String new_club_short;
    private int new_card_number;
    private int new_start_number;
    private String new_registration_id;
    private String new_category;


    public ChangeToServer(ChangedRunner changedRunner, Runner newRunner){
        this.old_given = changedRunner.getOldName();
        this.old_family = changedRunner.getOldSurname();
        this.old_start_time = changedRunner.getOldStartTime().getTime();
        this.old_club_short = changedRunner.getOldClubShort();
        this.old_card_number = changedRunner.getOldCardNumber();
        this.old_start_number = changedRunner.getOldStartNumber();
        this.old_registration_id = changedRunner.getOldRegistrationId();
        this.old_category = changedRunner.getOldCategory();

        this.new_given = newRunner.getName();
        this.new_family = newRunner.getSurname();
        this.new_start_time = newRunner.getStartTime().getTime();
        this.new_club_short = newRunner.getClubShort();
        this.new_card_number = newRunner.getCardNumber();
        this.new_start_number = newRunner.getStartNumber();
        this.new_registration_id = newRunner.getRegistrationId();
        this.new_category = newRunner.getCategory();
    }


    public String ToJson(){
        Gson json = new Gson();
        return json.toJson(this);
    }

}
