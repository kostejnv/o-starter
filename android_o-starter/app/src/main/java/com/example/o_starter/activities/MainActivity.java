package com.example.o_starter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView competitionsRecView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeComponents();



        CompetitionsRecViewAdapter adapter = new CompetitionsRecViewAdapter(this);
        adapter.setCompetitions(setData());

        competitionsRecView.setAdapter(adapter);
        competitionsRecView.setLayoutManager(new LinearLayoutManager(this));
        competitionsRecView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Log.i(TAG, "competition recycler view created");

    }

    private void InitializeComponents() {
        competitionsRecView = findViewById(R.id.competitionsRecView);
        Log.i(TAG, "components initialized");

    }

    private ArrayList<CompetitionBase> setData(){
        //TODO: dabase
        ArrayList<CompetitionBase> competitions = new ArrayList<CompetitionBase>();
        competitions.add(new CompetitionBase("MCR Kratka", "13.6.2021", null));
        competitions.add(new CompetitionBase("Oblastak Praha", "6.6.2021", null));
        Log.i(TAG, "test data setted");
        return competitions;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        Log.i(TAG, "option menu inflated");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //TODO: make actions
        switch (item.getItemId()){
            case R.id.new_competition_item:
                return true;
            case R.id.setting_item:
                return true;
            case R.id.about_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}