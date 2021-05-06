package com.example.o_starter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.o_starter.R;

public class NewCompetitionActivity extends AppCompatActivity {

    private static final String TAG = "NewCompetitionAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_competition_dialog);
        Log.i(TAG, "created");
    }
}