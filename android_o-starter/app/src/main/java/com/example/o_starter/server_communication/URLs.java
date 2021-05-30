package com.example.o_starter.server_communication;

import android.content.Context;
import android.util.Log;

import com.example.o_starter.EnviromentVariables;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;

import java.io.IOException;

/**
 * Class with control of Server URLs
 */
public class URLs {
    private static final String MAIN_URL = EnviromentVariables.SERVER_DOMAIN +"startlists/";
    private static final String TAG = "URLs";

    public static String GetCreateRaceURL(){
        return MAIN_URL + "create_race";
    }

    public static String GetSendDataURL(String serverRaceId){
        return MAIN_URL + serverRaceId + "/send_data";
    }

    /**
     * @return  URL of web side that shows on server uploaded changes if reachable else null
     */
    public static String GetChangesViewURL(Competition competition, Context context) {
        if (competition.getServerId() == null) {
            //resolve problem with no server id
            if (!competition.getSettings().getSendOnServer() || !ServerCommunicator.getInstance(context).CreateRaceOnServer(competition.getId())) {
                return null;
            }
            //update competition with serverId
            else{
                competition = StartlistsDatabase.getInstance(context).competitionDao().GetCompetitionById(competition.getId());
            }
        }
        //if get server id
            return MAIN_URL + competition.getServerId() + "/";
    }
}
