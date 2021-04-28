package com.example.o_starter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class  CompetitionsRecViewAdapter extends RecyclerView.Adapter<CompetitionsRecViewAdapter.ViewHolder> {

    private ArrayList<CompetitionBase> competitions = new ArrayList<CompetitionBase>();
    private Context context;
    private static final String TAG = "CompetitionsAdapter";

    public CompetitionsRecViewAdapter(Context context){
        this.context = context;
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
        holder.competitionTextView.setText(competitions.get(position).getName());
        holder.dateTextView.setText((competitions.get(position).getDate()));
        Log.i(TAG, "Set competitions items");


    }

    @Override
    public int getItemCount() {
        return competitions.size();
    }

    public void setCompetitions(ArrayList<CompetitionBase> competitions) {
        this.competitions = competitions;
        notifyDataSetChanged();
        Log.i(TAG, "Competitions list added");
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

            menuImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, menuImageView);
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

            PreferenceManager.setDefaultValues(context, R.xml.preferences_startlist, false);
        }

        private void InitializeComponents(@NonNull View itemView) {
            startImageView = itemView.findViewById(R.id.startImageView);
            menuImageView = itemView.findViewById(R.id.menuImageView);
            competitionTextView = itemView.findViewById(R.id.competitioTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            Log.i(TAG, "initialize components in competition item");
        }


    }
}
