package com.example.o_starter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.o_starter.R;
import com.example.o_starter.adapters.CompetitionsRecViewAdapter;
import com.example.o_starter.adapters.MinutesRecViewAdapter;

import java.security.InvalidParameterException;

public class StartlistViewActivity extends AppCompatActivity {

    public static final String COMPETITION_ID_INTENT = "COMPETITION_ID";
    public static final String TAG = "StartlistViewAct";
    private RecyclerView minuteRecView;
    private MinutesRecViewAdapter adapter;
    private int competitionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startlist_view);

        InitializeComponents();

        competitionId = getIntent().getIntExtra(COMPETITION_ID_INTENT, -1);
        if (competitionId == -1){
            Log.e(TAG, "competitionId was not send", new InvalidParameterException());
        }

        adapter = new MinutesRecViewAdapter(this, competitionId);

        minuteRecView.setAdapter(adapter);
        minuteRecView.setLayoutManager(new LinearLayoutManager(this));
        minuteRecView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Log.i(TAG, "minute recycler view created");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.startlist_competition_menu, menu);
        Log.i(TAG, "option menu inflated");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.startlist_close_competition:
                return true;
            case R.id.startlist_setting_item:
                return true;
            case R.id.startlist_share_changes_item:
                return true;
            case R.id.startlist_show_changes_item:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void InitializeComponents() {
        minuteRecView = findViewById(R.id.minute_rec_view);
    }
}