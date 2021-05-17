package com.example.o_starter.server_communication;

import com.example.o_starter.EnviromentVariables;

/**
 * Class with control of Server URLs
 */
public class URLs {
    private static final String MAIN_URL = EnviromentVariables.SERVER_DOMAIN +"startlists/";

    public static String GetCreateRaceURL(){
        return MAIN_URL + "create_race";
    }

    public static String GetSendDataURL(int serverRaceId){
        return MAIN_URL + serverRaceId + "/send_data";
    }
}
