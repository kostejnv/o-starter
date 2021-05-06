package com.example.o_starter.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Runner;

import java.util.Date;
import java.util.List;

public class RunnerRecViewAdapter extends RecyclerView.Adapter<RunnerRecViewAdapter.ViewHolder> {

    private Context context;
    public static final String TAG = "RunnerRecView";
    private int competitionId;
    private Date startTime;
    private List<String> categoriesToShow;
    private List<Runner> runners;

    public RunnerRecViewAdapter(Context context, int competitionId, Date startTime) {
        this.context = context;
        this.competitionId = competitionId;
        this.startTime = startTime;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.runner_list_item, parent, false);
        RunnerRecViewAdapter.ViewHolder holder = new RunnerRecViewAdapter.ViewHolder(view);
        Log.i(TAG, "ViewHolder created");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TryInitializeData();
        holder.SItextView.setText(String.valueOf(runners.get(position).getCardNumber()));
        holder.runnerNametextView.setText(String.format(runners.get(position).getName() + runners.get(position).getSurname()));
        holder.startNumbertextView.setText(String.valueOf(runners.get(position).getStartNumber()));

    }

    private void TryInitializeData(){
        if (categoriesToShow == null){
            categoriesToShow = StartlistsDatabase.getInstance(context).competitionDao().GetCategoriesToShow(competitionId).getCategoriesToShow();
        }
        if (runners == null){
            runners = StartlistsDatabase.getInstance(context).runnerDao().GetRunnersInMinute(competitionId,categoriesToShow, startTime);
        }
    }

    @Override
    public int getItemCount() {
        TryInitializeData();
        return runners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView SItextView;
        private TextView runnerNametextView;
        private TextView startNumbertextView;
        private TextView registrationIDtextView;
        private ImageView editImageView;
        private CheckBox StartedcheckBox;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitializeComponents(itemView);
        }

        private void InitializeComponents(@NonNull View itemView) {
            SItextView = itemView.findViewById(R.id.SItextView);
            runnerNametextView = itemView.findViewById(R.id.runnerNametextView);
            startNumbertextView = itemView.findViewById(R.id.startNumbertextView);
            registrationIDtextView = itemView.findViewById(R.id.registrationIDtextView);
            editImageView = itemView.findViewById(R.id.editImageView);
            StartedcheckBox = itemView.findViewById(R.id.StartedcheckBox);
            Log.i(TAG, "initialize components in minute item");
        }
    }
}
