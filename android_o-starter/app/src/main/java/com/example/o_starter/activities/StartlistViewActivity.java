package com.example.o_starter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.o_starter.R;
import com.example.o_starter.adapters.CompetitionsRecViewAdapter;
import com.example.o_starter.adapters.MinutesRecViewAdapter;

import java.security.InvalidParameterException;

public class StartlistViewActivity extends AppCompatActivity {

    public static final String TAG = "StartlistViewAct";
    private RecyclerView minuteRecView;
    private MinutesRecViewAdapter adapter;
    private int competitionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startlist_view);

        InitializeComponents();

        competitionId = getIntent().getIntExtra("COMPETITION_ID", -1);
        if (competitionId == -1){
            Log.e(TAG, "competitionId was not send", new InvalidParameterException());
        }

        adapter = new MinutesRecViewAdapter(this, competitionId);

        minuteRecView.setAdapter(adapter);
        minuteRecView.setLayoutManager(new LinearLayoutManager(this));
        minuteRecView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Log.i(TAG, "minute recycler view created");
    }

    private void InitializeComponents() {
        minuteRecView = findViewById(R.id.minute_rec_view);
    }
}