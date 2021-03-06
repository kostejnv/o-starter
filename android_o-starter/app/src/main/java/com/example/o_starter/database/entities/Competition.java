package com.example.o_starter.database.entities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.o_starter.R;
import com.example.o_starter.activities.StartlistViewActivity;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.converters.ListDateToStringConverter;
import com.example.o_starter.database.converters.DateToLongConverter;
import com.example.o_starter.server_communication.ServerCommunicator;
import com.example.o_starter.server_communication.URLs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

/**
 * Entity for table "competitons" in database.
 * <p>
 * It contains general information about competition, its states and its settings
 */
@Entity(tableName = "competitions")
public class Competition {

    @Ignore
    private static final String TAG = "CompetitionEnt";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @ColumnInfo(name = "start_time")
    @TypeConverters(DateToLongConverter.class)
    private Date startTime;

    @ColumnInfo(name = "minutes_with_runner")
    @TypeConverters(ListDateToStringConverter.class)
    private List<Date> minutesWithRunner;

    @ColumnInfo(name = "server_id")
    private String serverId;

    @ColumnInfo(name = "was_finished")
    private boolean wasFinished;

    @Embedded
    private CompetitionSettings settings;

    @Ignore
    public Competition() {
    }

    @Ignore
    public Competition(int id, String name, Date startTime, List<Date> minutesWithRunner, String serverId, boolean wasFinished, CompetitionSettings settings) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.minutesWithRunner = minutesWithRunner;
        this.serverId = serverId;
        this.wasFinished = wasFinished;
        this.settings = settings;
    }

    public Competition(String name, Date startTime, List<Date> minutesWithRunner, String serverId, boolean wasFinished, CompetitionSettings settings) {
        this.name = name;
        this.startTime = startTime;
        this.minutesWithRunner = minutesWithRunner;
        this.serverId = serverId;
        this.wasFinished = wasFinished;
        this.settings = settings;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public CompetitionSettings getSettings() {
        return settings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setSettings(CompetitionSettings settings) {
        this.settings = settings;
    }

    public List<Date> getMinutesWithRunner() {
        return minutesWithRunner;
    }

    public void setMinutesWithRunner(List<Date> minutesWithRunner) {
        this.minutesWithRunner = minutesWithRunner;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public boolean isWasFinished() {
        return wasFinished;
    }

    public void setWasFinished(boolean wasFinished) {
        this.wasFinished = wasFinished;
    }

    /**
     * Set information that can be obtained from runners
     *
     * @param runners collection of runner of given competition
     */
    @SuppressLint("SimpleDateFormat")
    @Ignore
    public void SetInfoByRunners(Collection<Runner> runners) {
        CompetitionSettings settings = new CompetitionSettings();

        HashSet<String> allCategories = new HashSet<String>();

        SortedSet<Date> minutesSet = new TreeSet<Date>(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return (int) (o1.getTime() - o2.getTime());
            }
        });

        for (Runner runner : runners) {
            allCategories.add(runner.getCategory());
            minutesSet.add(runner.getStartTime());
        }
        settings.setCategoriesToShow(new ArrayList<String>(allCategories));
        settings.setAllCategories(new ArrayList<String>(allCategories));
        setSettings(settings);
        setStartTime(minutesSet.first());
        setMinutesWithRunner(new ArrayList<Date>(minutesSet));
        Log.i(TAG, String.format("get all parameters from runner to competition " + getId() + ", first minute is " + new SimpleDateFormat("hh:mm").format(minutesSet.first())));
    }

    @Ignore
    /**
     * get share offer for URL of server changes view if possible
     */
    public void shareChange(int competitionId, Context context) {
        if (getSettings().getSendOnServer()) {

            //get URL
            GetShareURLAsyncTask getShareURL = new GetShareURLAsyncTask(context);
            String shareURL = null;
            try {
                shareURL = getShareURL.execute().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            if (shareURL != null) {
                //shareURL
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareURL);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);
            } else {
                Toast.makeText(context, R.string.connection_failed, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.change_synchronisation_with_server, Toast.LENGTH_SHORT).show();
        }
    }

    private class GetShareURLAsyncTask extends AsyncTask<Void,Void,String>{
        private Context context;

        public GetShareURLAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return URLs.GetChangesViewURL(Competition.this, context);
        }
    }

    /**
     * Make competition finished and try send unstarted competitiors on Server if possible
     *
     * @param context
     */
    @Ignore
    public void FinishCompetition(Context context) {
        if (getSettings().getSendOnServer()) {
            boolean wasSuccessful;
            new SendUnstartedTOServerAsyncTask(context, this).execute();
        }
        //make competition finished and update in database
        setWasFinished(true);
        StartlistsDatabase.getInstance(context).competitionDao().updateSingleCompetition(this);
    }

    /**
     * AsyncTask that cares about sending unstarted runners to Server
     */
    private class SendUnstartedTOServerAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private Context context;
        private Competition competition;

        public SendUnstartedTOServerAsyncTask(Context context, Competition competition) {
            this.context = context;
            this.competition = competition;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //check if should be send unstarted to server

            //send unstarted to server
            StartlistsDatabase.getInstance(context).unsentUnstartedDao().DeleteAllUnstarted();
            List<Runner> unstartedRUnners = StartlistsDatabase.getInstance(context).runnerDao().GetUnstartedRunners(competition.getId());
            for (Runner runner : unstartedRUnners) {
                UnsentUnstertedRunner unsentRunner = new UnsentUnstertedRunner(runner.getId());
                StartlistsDatabase.getInstance(context).unsentUnstartedDao().insertSingleRunner(unsentRunner);
            }
            return ServerCommunicator.getInstance(context).SendDataToServer(competition.getId());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(!aBoolean) {
                Toast.makeText(context, R.string.unstarted_failed, Toast.LENGTH_LONG).show();
            }
        }
    }
}
