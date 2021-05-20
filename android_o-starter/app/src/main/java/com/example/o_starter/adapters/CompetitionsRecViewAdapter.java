package com.example.o_starter.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.DatabaseUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.activities.MainActivity;
import com.example.o_starter.activities.SettingsStartlistActivity;
import com.example.o_starter.activities.StartlistViewActivity;
import com.example.o_starter.activities.ViewChangesActivity;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.startlist_settings.SettingsStartlistFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Recycler view adapter for all competitions in {@link com.example.o_starter.activities.MainActivity MainActivity}
 */
public class  CompetitionsRecViewAdapter extends RecyclerView.Adapter<CompetitionsRecViewAdapter.ViewHolder> {

    private final Context context;
    private static final String TAG = "CompetitionsAdapter";
    private List<Competition> competitions;
    private final View parent;

    /**
     *
     * @param context context of parent
     * @param parent parent view
     */
    public CompetitionsRecViewAdapter(Context context, View parent) {
        this.context = context;
        this.parent = parent;
    }

    /**
     *Self-documenting
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.competitions_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        Log.i(TAG, "ViewHolder created");
        return holder;
    }

    /**
     * Set components for each item in recycler view
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        competitions = StartlistsDatabase.getInstance(context).competitionDao().GetAllCompetition();
        holder.competitionTextView.setText(competitions.get(position).getName());
        Date date = competitions.get(position).getStartTime();
        holder.dateTextView.setText(new SimpleDateFormat("E dd.MM.yyyy").format(date));

        //Setting menu functionalities
        holder.menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.menuImageView);
                popup.getMenuInflater().inflate(R.menu.competition_menu, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Competition currentCompetition = StartlistsDatabase.getInstance(context).competitionDao().GetCompetitionById(competitions.get(position).getId());
                        switch (item.getItemId()) {
                            case R.id.setting_item:
                                Intent intent = new Intent(context, SettingsStartlistActivity.class);
                                intent.putExtra(SettingsStartlistFragment.TAG_COMPETITION_ID, competitions.get(position).getId());
                                //return result if competition data was changed - updating components
                                ((Activity)context).startActivityForResult(intent, MainActivity.REQUEST_SETTINGS_CHAGED);
                                Log.i(TAG, "open settings activity");
                                break;
                            case R.id.share_changes_item:
                                currentCompetition.shareChange(currentCompetition.getId(),context);
                                break;
                            case R.id.show_changes_item:
                                Intent intent1 = new Intent(context, ViewChangesActivity.class);
                                intent1.putExtra(ViewChangesActivity.COMPETITION_ID_TAG, currentCompetition.getId());
                                context.startActivity(intent1);
                                break;
                            case R.id.delete_race_item:
                                new AlertDialog.Builder(context)
                                        .setTitle(R.string.delete_competition)
                                        .setMessage(R.string.really_delete_competition)

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                int competitionId = competitions.get(position).getId();
                                                DeleteCompetitionAsyncTask deleteTask = new DeleteCompetitionAsyncTask();
                                                deleteTask.execute(competitionId);
                                                ((DatabaseUpdateListener)context).OnDBUpdate();
                                            }
                                        })
                                        .setNegativeButton(R.string.no, null)
                                        .show();
                                break;
                            default:
                                throw new IllegalStateException(context.getString(R.string.not_implemented));

                        }
                        return true;
                    }
                });

                popup.show();
                Log.i(TAG, "Popup menu opened");
            }
        });

        //go to StartlistViewActivity after clik on start image
        holder.startImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StartlistViewActivity.class);
                intent.putExtra(StartlistViewActivity.COMPETITION_ID_INTENT, competitions.get(position).getId());
                context.startActivity(intent);
                Log.i(TAG, "startlist view activity started");
            }
        });

        Log.i(TAG, "Set competitions items");


    }

    /**
     * @return number of competiton
     */
    @Override
    public int getItemCount() {
        return StartlistsDatabase.getInstance(context).competitionDao().GetCompetitionCount();
    }

    /**
     * Holder of views for each item
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView startImageView;
        private ImageView menuImageView;
        private TextView competitionTextView;
        private TextView dateTextView;
        private static final String TAG = "CompetitionsViewHolder";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitializeComponents(itemView);
        }

        private void InitializeComponents(@NonNull View itemView) {
            startImageView = itemView.findViewById(R.id.startImageView);
            menuImageView = itemView.findViewById(R.id.menuImageView);
            competitionTextView = itemView.findViewById(R.id.competitioTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            Log.i(TAG, "initialize components in competition item");
        }


    }

    /**
     * Self-documenting
     */
    private class DeleteCompetitionAsyncTask extends AsyncTask<Integer, Void,Void>{
        @Override
        protected Void doInBackground(Integer... integers) {
            int competitionId = integers[0];
            StartlistsDatabase.getInstance(context).competitionDao().DeleteById(competitionId);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            notifyDataChanged();
        }
    }

    /**
     * Notify adapter about change in competition list and update TextView about No competition
     */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        if(StartlistsDatabase.getInstance(context).competitionDao().GetAllCompetition().size()==0){
            parent.findViewById(R.id.addCompetitionTextview).setVisibility(View.VISIBLE);
        }
        else
        {
            parent.findViewById(R.id.addCompetitionTextview).setVisibility(View.GONE);
        }
    }

}
