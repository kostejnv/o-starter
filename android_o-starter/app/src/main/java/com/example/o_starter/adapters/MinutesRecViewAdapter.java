package com.example.o_starter.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Recycler view for each minute with runner for given competiton in {@link com.example.o_starter.activities.StartlistViewActivity StartlistViewActivity}
 */
public class MinutesRecViewAdapter extends RecyclerView.Adapter<MinutesRecViewAdapter.ViewHolder> {
    private final Context context;
    private static final String TAG = "MinuteAdapter";
    private List<Date> sortedMinutes;
    private final int competitionId;
    private RunnerRecViewAdapter adapter;

    /**
     *
     * @param context context of parent
     */
    public MinutesRecViewAdapter(Context context, int competitionId) {
        this.context = context;
        this.competitionId = competitionId;
    }


    /**
     * Self-documenting
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minute_list_item, parent, false);
        MinutesRecViewAdapter.ViewHolder holder = new ViewHolder(view);
        Log.i(TAG, "ViewHolder created");
        return holder;
    }

    /**
     * setting all components of view holder
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (sortedMinutes == null){
            sortedMinutes = StartlistsDatabase.getInstance(context).competitionDao().GetMinutesWithRunnerById(competitionId).getMinutesWithRunner();
        }

        holder.timeTextView.setText(new SimpleDateFormat("hh:mm:ss").format(sortedMinutes.get(position)).toString());
        Log.i(TAG, "Time set on " + new SimpleDateFormat("hh:mm:ss").format(sortedMinutes.get(position)).toString());

        adapter = new RunnerRecViewAdapter(context, competitionId, sortedMinutes.get(position));

        holder.runnersRecView.setAdapter(adapter);
        holder.runnersRecView.setLayoutManager(new LinearLayoutManager(context));
        holder.runnersRecView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        Log.i(TAG, "runner recycler view created");
    }

    /**
     *
     * @return number of minutes with runners
     */
    @Override
    public int getItemCount() {
        if (sortedMinutes == null){
            sortedMinutes = StartlistsDatabase.getInstance(context).competitionDao().GetMinutesWithRunnerById(competitionId).getMinutesWithRunner();
        }
        return sortedMinutes.size();
    }

    /**
     * Holder of views for each minute
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView timeTextView;
        private RecyclerView runnersRecView;

        /**
         * Self-documenting
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitializeComponents(itemView);
        }

        /**
         * Self-documenting
         */
        private void InitializeComponents(@NonNull View itemView) {
            timeTextView = itemView.findViewById(R.id.time_text_view);
            runnersRecView = itemView.findViewById(R.id.runnersRecView);
            Log.i(TAG, "initialize components in minute item");
        }
    }
}
