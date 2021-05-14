package com.example.o_starter.server_communication;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.util.Log;

import com.example.o_starter.EnviromentVariables;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.ChangedRunner;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;
import com.example.o_starter.database.entities.UnsentChange;
import com.example.o_starter.database.entities.UnsentUnstertedRunner;
import com.example.o_starter.database.entities.UnstartedRunner;
import com.example.o_starter.server_communication.entities.ChangeToServer;
import com.example.o_starter.server_communication.entities.CompetitionFromServer;
import com.example.o_starter.server_communication.entities.CompetitionToServer;
import com.example.o_starter.server_communication.entities.DataToServer;
import com.example.o_starter.server_communication.entities.UnstartedToServer;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ServerCommunicator {

    public static final String TAG = "SeverCominucator";
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
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/json");
            //connection.setRequestProperty("Content-Length", Integer.toString(output.length()));
            connection.setDoOutput(true);
            connection.setDoInput(true);


            //For testing purpose
            if(EnviromentVariables.MODE == EnviromentVariables.Mode.TEST) {
                connection.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                connection.setHostnameVerifier(new AllowAllHostnameVerifier());
            }


            connection.getOutputStream().write(output.getBytes(StandardCharsets.UTF_8));

            InputStream inputStream = connection.getInputStream();

            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = inputStream.read()) != -1; ) {
                sb.append((char) ch);
            }

            CompetitionFromServer competitionFromServer = CompetitionFromServer.getFromJSON(sb.toString());

            competition.setServerId(competitionFromServer.getId());
            Log.i(TAG, "server id: " + competitionFromServer.getId());

            StartlistsDatabase.getInstance(context).competitionDao().updateSingleCompetition(competition);
            return true;

        } catch (IOException e) {
            return false;
        }

    }


    public boolean SendDataToServer(int competitionId){

        int competitionServerId = StartlistsDatabase.getInstance(context).competitionDao().GetCompetitionById(competitionId).getServerId();

        if (competitionServerId == 0){
            if(!CreateRaceOnServer(competitionId))
            {
                return false;
            }
            else{
                competitionServerId = StartlistsDatabase.getInstance(context).competitionDao().GetCompetitionById(competitionId).getServerId();
            }
        }

        List<UnsentChange> unsentChanges = StartlistsDatabase.getInstance(context).unsentChangedDao().GetUnsentChangesByCompetitionId(competitionId);
        List<UnsentUnstertedRunner> unsentUnstertedRunners = StartlistsDatabase.getInstance(context).unsentUnstartedDao().GetUnsentUnstartedByCompetitionId(competitionId);

        ArrayList<ChangeToServer> changesToServer = new ArrayList<ChangeToServer>();
        for(UnsentChange unsentChange:unsentChanges){
            ChangedRunner changedRunner = StartlistsDatabase.getInstance(context).changedRunnerDao().getById(unsentChange.getChangedRunnerId());
            Runner newRunner =StartlistsDatabase.getInstance(context).runnerDao().getById(changedRunner.getRunnerId());
            changesToServer.add(new ChangeToServer(changedRunner,newRunner));
        }


        ArrayList<UnstartedToServer> unstartedsToServer = new ArrayList<UnstartedToServer>();
        for(UnsentUnstertedRunner unsentUnstertedRunner :unsentUnstertedRunners){
            UnstartedRunner unstartedRunner = StartlistsDatabase.getInstance(context).unstartedRunnerDao().GetById(unsentUnstertedRunner.getUnstartedRunnerId());
            Runner runner = StartlistsDatabase.getInstance(context).runnerDao().getById(unstartedRunner.getRunnerId());
            unstartedsToServer.add(new UnstartedToServer(runner));
        }

        DataToServer dataToServer = new DataToServer(changesToServer, unstartedsToServer);
        String output = dataToServer.ToJson();

        try {

            URL url = new URL(Domains.GetSendDataDomain(competitionServerId));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            //For testing purpose
            if(EnviromentVariables.MODE == EnviromentVariables.Mode.TEST) {
                connection.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                connection.setHostnameVerifier(new AllowAllHostnameVerifier());
            }


            connection.getOutputStream().write(output.getBytes(StandardCharsets.UTF_8));

            if(connection.getResponseCode() == 200){
                StartlistsDatabase.getInstance(context).unsentUnstartedDao().deleteRunners(unsentUnstertedRunners);
                StartlistsDatabase.getInstance(context).unsentChangedDao().deleteChanges(unsentChanges);
                return true;
            }
            else
            {
                return false;
            }

        } catch (IOException e) {
            return false;
        }

    }
}
