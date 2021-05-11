package com.example.o_starter.server_communication;

import android.content.Context;

import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.server_communication.entities.CompetitionFromServer;
import com.example.o_starter.server_communication.entities.CompetitionToServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServerCommunicator {

    private static ServerCommunicator instance;
    private static Context context;

    public static ServerCommunicator getInstance(Context context) {
        if (instance == null) {
            instance = new ServerCommunicator(context);
        }
        return instance;
    }

    public ServerCommunicator(Context context) {
        this.context = context;
    }

    public boolean CreateRaceOnServer(int competitionId) {
        Competition competition = StartlistsDatabase.getInstance(context).competitionDao().GetCompetitionById(competitionId);

        String output = new CompetitionToServer(competition).ToJson();

        try {
            URL url = new URL(Domains.GetCreateRaceDomain());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/json");
            //connection.setRequestProperty("Content-Length", Integer.toString(output.length()));
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.getOutputStream().write(output.getBytes(StandardCharsets.UTF_8));

            InputStream inputStream = connection.getInputStream();

            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = inputStream.read()) != -1; ) {
                sb.append((char) ch);
            }

            CompetitionFromServer competitionFromServer =  CompetitionFromServer.getFromJSON(sb.toString());

            competition.setServerId(competitionFromServer.getId());

            StartlistsDatabase.getInstance(context).competitionDao().updateSingleCompetition(competition);
            return true;

        } catch (IOException e) {
            return false;
        }

    }
}
