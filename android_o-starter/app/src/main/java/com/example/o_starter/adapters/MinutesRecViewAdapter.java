package com.example.o_starter.adapters;

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

public class MinutesRecViewAdapter extends RecyclerView.Adapter<MinutesRecViewAdapter.ViewHolder> {
    private Context context;
    public static final String TAG = "MinuteAdapter";
    private List<Date> sortedMinutes;
    private int competitionId;
    private RunnerRecViewAdapter adapter;

    public MinutesRecViewAdapter(Context context, int competitionId) {
        this.context = context;
        this.competitionId = competitionId;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.minute_list_item, parent, false);
        MinutesRecViewAdapter.ViewHolder holder = new MinutesRecViewAdapter.ViewHolder(view);
        Log.i(TAG, "ViewHolder created");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (sortedMinutes == null){
            //TODO: jiny thread
            sortedMinutes = StartlistsDatabase.getInstance(context).competitionDao().GetMinutesWithRunnerById(competitionId).getMinutesWithRunner();
        }

        holder.timeTextView.setText(new SimpleDateFormat("hh:mm:ss").format(sortedMinutes.get(position)).toString());
        Log.i(TAG, String.format("Time set on " + new SimpleDateFormat("hh:mm:ss").format(sortedMinutes.get(position)).toString()));

        adapter = new RunnerRecViewAdapter(context, competitionId, sortedMinutes.get(position));

        holder.runnersRecView.setAdapter(adapter);
        holder.runnersRecView.setLayoutManager(new LinearLayoutManager(context));
        holder.runnersRecView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        Log.i(TAG, "runner recycler view created");
    }

    @Override
    public int getItemCount() {
        if (sortedMinutes == null){
            sortedMinutes = StartlistsDatabase.getInstance(context).competitionDao().GetMinutesWithRunnerById(competitionId).getMinutesWithRunner();
        }
        return sortedMinutes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView timeTextView;
        private ImageView arrowImageView;
        private RecyclerView runnersRecView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitializeComponents(itemView);
            PreferenceManager.setDefaultValues(context, R.xml.preferences_startlist, false);
        }

        private void InitializeComponents(@NonNull View itemView) {
            timeTextView = itemView.findViewById(R.id.time_text_view);
            arrowImageView = itemView.findViewById(R.id.arrow_up_image_view);
            runnersRecView = itemView.findViewById(R.id.runnersRecView);
            Log.i(TAG, "initialize components in minute item");
        }
    }
}
