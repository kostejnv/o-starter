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
import android.widget.TextView;

import com.example.o_starter.CompetitionsUpdateListener;
import com.example.o_starter.adapters.CompetitionsRecViewAdapter;
import com.example.o_starter.R;

/**
 * Class for launching activity with competition recycler view
 */
public class MainActivity extends AppCompatActivity implements CompetitionsUpdateListener {

    private RecyclerView competitionsRecView;
    private TextView addCompetitionTextview;

    private static final String TAG = "MainActivity";
    private CompetitionsRecViewAdapter adapter;


    /**
     *Initialize and set all functions of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeComponents();

        // Set and show competition adapter
        adapter = new CompetitionsRecViewAdapter(this, findViewById(android.R.id.content));
        competitionsRecView.setAdapter(adapter);
        competitionsRecView.setLayoutManager(new LinearLayoutManager(this));
        competitionsRecView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.notifyDataChanged();
        Log.i(TAG, "competition recycler view created");

    }

    /**
     * Self-Documenting
     */
    private void InitializeComponents() {
        competitionsRecView = findViewById(R.id.competitionsRecView);
        addCompetitionTextview = findViewById(R.id.addCompetitionTextview);
        Log.i(TAG, "components initialized");

    }


    /**
     *Build main menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        Log.i(TAG, "option menu inflated");
        return true;
    }

    /**
     *set function for menu items
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_competition_item:
                DialogFragment newFragment = new NewCompetitionDialog();
                newFragment.show(getSupportFragmentManager(), "new competition");
                return true;
            case R.id.about_item:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.about_app)
                        .setMessage(R.string.about_app_text)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Notify competition adapter that data were changed
     *
     * For updating competition recycler view purpose
     */
    @Override
    public void OnDBUpdate() {
        adapter.notifyDataChanged();
    }
}

