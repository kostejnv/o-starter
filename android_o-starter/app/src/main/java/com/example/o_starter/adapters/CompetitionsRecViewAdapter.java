package com.example.o_starter.adapters;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.CompetitionsUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.activities.SettingsStartlistActivity;
import com.example.o_starter.activities.StartlistViewActivity;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class  CompetitionsRecViewAdapter extends RecyclerView.Adapter<CompetitionsRecViewAdapter.ViewHolder> {

    private Context context;
    private static final String TAG = "CompetitionsAdapter";
    private List<Competition> competitions;
    private View parent;

    public CompetitionsRecViewAdapter(Context context, View parent) {
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.competitions_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        Log.i(TAG, "ViewHolder created");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO:jiny thread
        competitions = StartlistsDatabase.getInstance(context).competitionDao().GetAllCompetition();
        holder.competitionTextView.setText(competitions.get(position).getName());
        Date date = competitions.get(position).getStartTime();
        holder.dateTextView.setText(new SimpleDateFormat("E dd.MM.yyyy").format(date));


        holder.menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.menuImageView);
                popup.getMenuInflater().inflate(R.menu.competition_menu, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.setting_item:
                                Intent intent = new Intent(context, SettingsStartlistActivity.class);
                                context.startActivity(intent);
                                Log.i(TAG, "open settings activity");
                                break;
                            case R.id.share_changes_item:
                                break;
                            case R.id.show_changes_item:
                                break;
                            case R.id.share_race_item:
                                break;
                            case R.id.delete_race_item:
                                new AlertDialog.Builder(context)
                                        .setTitle("Delete Competition")
                                        .setMessage("Are you sure you want to delete this Competition?")

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                int competitionId = competitions.get(position).getId();
                                                DeleteCompetitionAsyncTask deleteTask = new DeleteCompetitionAsyncTask();
                                                deleteTask.execute(competitionId);
                                                ((CompetitionsUpdateListener)context).OnDBUpdate();
                                            }
                                        })
                                        .setNegativeButton("No", null)
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

        holder.startImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StartlistViewActivity.class);
                intent.putExtra("COMPETITION_ID", competitions.get(position).getId());
                context.startActivity(intent);
                Log.i(TAG, "startlist view activity started");
            }
        });

        Log.i(TAG, "Set competitions items");


    }



    @Override
    public int getItemCount() {
        return StartlistsDatabase.getInstance(context).competitionDao().GetCompetitionCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView startImageView;
        private ImageView menuImageView;
        private TextView competitionTextView;
        private TextView dateTextView;
        private static final String TAG = "CompetitionsViewHolder";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitializeComponents(itemView);
            //PreferenceManager.setDefaultValues(context, R.xml.preferences_startlist, false);
        }

        private void InitializeComponents(@NonNull View itemView) {
            startImageView = itemView.findViewById(R.id.startImageView);
            menuImageView = itemView.findViewById(R.id.menuImageView);
            competitionTextView = itemView.findViewById(R.id.competitioTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            Log.i(TAG, "initialize components in competition item");
        }


    }

    private class DeleteCompetitionAsyncTask extends AsyncTask<Integer, Void,Void>{
        @Override
        protected Void doInBackground(Integer... integers) {
            int competitionId = integers[0];
            StartlistsDatabase.getInstance(context).competitionDao().DeleteById(competitionId);
            return null;
        }
    }

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
