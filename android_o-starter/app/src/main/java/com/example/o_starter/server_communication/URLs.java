package com.example.o_starter.server_communication;

/**
 * Class with control of Server URLs
 */
public class URLs {
    //TODO:to enviroments
    private static final String MAIN_DOMAIN = "https://10.0.2.2:8000/startlists/";

    public static String GetCreateRaceURL(){
        return MAIN_DOMAIN + "create_race";
    }

    public static String GetSendDataURL(int serverRaceId){
        return MAIN_DOMAIN + serverRaceId + "/send_data";
    }
}
