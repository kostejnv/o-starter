package com.example.o_starter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.o_starter.CompetitionBase;
import com.example.o_starter.DialogFragUpdateListener;
import com.example.o_starter.adapters.CompetitionsRecViewAdapter;
import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogFragUpdateListener {

    private RecyclerView competitionsRecView;
    private TextView addCompetitionTextview;

    private static final String TAG = "MainActivity";
    private CompetitionsRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeComponents();




        adapter = new CompetitionsRecViewAdapter(this, findViewById(android.R.id.content));

        competitionsRecView.setAdapter(adapter);
        competitionsRecView.setLayoutManager(new LinearLayoutManager(this));
        competitionsRecView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter.notifyDataChanged();
        Log.i(TAG, "competition recycler view created");

    }

    private void InitializeComponents() {
        competitionsRecView = findViewById(R.id.competitionsRecView);
        addCompetitionTextview = findViewById(R.id.addCompetitionTextview);
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
                DialogFragment newFragment = new NewCompetitionFragment();
                newFragment.show(getSupportFragmentManager(), "new competition");
                return true;
            case R.id.setting_item:
                return true;
            case R.id.about_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void OnDBUpdate() {
        adapter.notifyDataChanged();
    }
}

