package com.example.o_starter.activities.ui.view_changes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.R;
import com.example.o_starter.adapters.ChangesRecViewAdapter;
import com.example.o_starter.adapters.CompetitionsRecViewAdapter;
import com.example.o_starter.adapters.UnstartedRunnersRecViewAdapter;
import com.example.o_starter.database.StartlistsDatabase;


/**
 * fragment that shows list of unstarted runners for given competition in {@link com.example.o_starter.activities.ViewChangesActivity ViewChangesActivity}
 */
public class UnstartedFragment extends Fragment {

    public static final String COMPETITION_ID ="COMPETITION_ID";

    /**
     * Get new instance of fragment for given competition
     */
    public static UnstartedFragment newInstance(int competitionId) {
        UnstartedFragment fragment = new UnstartedFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt(COMPETITION_ID, competitionId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Self-documenting
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //get arguments
        Bundle args = getArguments();
        int competitionId = args.getInt(COMPETITION_ID, 0);

        View view = inflater.inflate(R.layout.fragment_rec_view, container, false);

        //intialize components
        RecyclerView unstartedRecView = view.findViewById(R.id.rec_view);

        // Set and show competition adapter
        UnstartedRunnersRecViewAdapter adapter = new UnstartedRunnersRecViewAdapter(competitionId, view.getContext());
        unstartedRecView.setAdapter(adapter);
        unstartedRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        unstartedRecView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }
}
