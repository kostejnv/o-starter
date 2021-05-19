package com.example.o_starter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Runner;

import java.util.List;

/**
 * Recycler view for each unstarted runner for given competiton in {@link com.example.o_starter.activities.ViewChangesActivity ViewChangesActivity}
 */
public class UnstartedRunnersRecViewAdapter extends RecyclerView.Adapter<UnstartedRunnersRecViewAdapter.ViewHolder> {

    private final List<Runner> unstartedRunners;
    private final int competitionId;

    /**
     *
     * @param context context of parent
     */
    public UnstartedRunnersRecViewAdapter(int competitionId, Context context) {
        this.competitionId = competitionId;
        unstartedRunners = StartlistsDatabase.getInstance(context).runnerDao().GetUnstartedRunners(competitionId);
    }


    /**
     * Self-documenting
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unstarted_runner_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * setting all components of view holder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Runner currentRunner = unstartedRunners.get(position);
        holder.regTextView.setText(currentRunner.getRegistrationId());
        holder.nameTextView.setText(currentRunner.getSurname() + " " + currentRunner.getName());
    }

    /**
     *
     * @return number of unstarted Runners for given competition
     */
    @Override
    public int getItemCount() {
        return unstartedRunners.size();
    }

    /**
     * Holder of views for each unstarted Runner
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView regTextView;
        private TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitializeComponents(itemView);
        }

        private void InitializeComponents(View itemView) {
            regTextView = itemView.findViewById(R.id.registrationIDtextView);
            nameTextView = itemView.findViewById(R.id.runnerNametextView);
        }
    }
}
