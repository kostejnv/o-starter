package com.example.o_starter.activities;

import android.os.Bundle;

import com.example.o_starter.DatabaseUpdateListener;
import com.example.o_starter.R;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.example.o_starter.activities.ui.view_changes.ViewChangesPagerAdapter;

/**
 * Activity for displaying changes and unstarted runners
 */
public class ViewChangesActivity extends AppCompatActivity implements DatabaseUpdateListener {

    public static final String COMPETITION_ID_TAG = "COMPETITION_ID";
    public static final String TAG = "ViewChangesAct";
    private ViewChangesPagerAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get parameters
        Bundle parameters = getIntent().getExtras();
        int competitionId = 0;
        if (parameters != null){
            competitionId =  parameters.getInt(COMPETITION_ID_TAG, 0);
        }
        if(competitionId == 0){
            Log.e(TAG, "No parameters passed");
        }

        setContentView(R.layout.activity_view_changes);
        sAdapter = new ViewChangesPagerAdapter(this, getSupportFragmentManager(), competitionId);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void OnDBUpdate() {
        sAdapter.notifyDataSetChanged();
    }
}