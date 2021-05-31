package com.example.o_starter.activities.ui.view_changes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.R;
import com.example.o_starter.adapters.ChangesRecViewAdapter;
import com.example.o_starter.adapters.CompetitionsRecViewAdapter;

/**
 * fragment that shows changes for given competition in {@link com.example.o_starter.activities.ViewChangesActivity ViewChangesActivity}
 */
public class ChangesFragment extends Fragment {

    private RecyclerView changesRecView;
    private static final String COMPETITION_ID ="COMPETITION_ID";

    /**
     * Get new instance of fragment for given competition
     */
    public static ChangesFragment newInstance(int competitionId) {
        ChangesFragment fragment = new ChangesFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt(COMPETITION_ID, competitionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rec_view, container, false);

        //intialize components
        changesRecView = view.findViewById(R.id.rec_view);

        //get arguments
        Bundle args = getArguments();
        int competitionId = args.getInt(COMPETITION_ID, 0);

        // Set and show competition adapter
        ChangesRecViewAdapter adapter = new ChangesRecViewAdapter(competitionId, view.getContext());
        changesRecView.setAdapter(adapter);
        changesRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        changesRecView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }
}
