package com.example.o_starter.server_communication;

public class Domains {

    private static final String MAIN_DOMAIN = "http://10.0.2.2:8000/startlists/";

    public static String GetCreateRaceDomain(){
        return MAIN_DOMAIN + "create_race";
    }

    public static String GetSendDataDomain(int serverRaceId){
        return MAIN_DOMAIN + serverRaceId + "/send_data";
    }
}
