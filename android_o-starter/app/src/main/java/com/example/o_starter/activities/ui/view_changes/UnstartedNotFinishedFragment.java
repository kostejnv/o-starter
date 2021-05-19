package com.example.o_starter.activities.ui.view_changes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.DatabaseUpdateListener;
import com.example.o_starter.R;
import com.example.o_starter.adapters.UnstartedRunnersRecViewAdapter;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.Competition;



/**
 * fragment that says that competition was not finished in {@link com.example.o_starter.activities.ViewChangesActivity ViewChangesActivity}
 */
public class UnstartedNotFinishedFragment extends Fragment {

    public static final String COMPETITION_ID ="COMPETITION_ID";

    /**
     * Get new instance of fragment for given competition
     */
    public static UnstartedNotFinishedFragment newInstance(int competitionId) {
        UnstartedNotFinishedFragment fragment = new UnstartedNotFinishedFragment();
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

        View view = inflater.inflate(R.layout.fragment_unstarted_not_finished, container, false);

        Button finisheButton = view.findViewById(R.id.finish_competition_button);
        finisheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Competition competition = StartlistsDatabase.getInstance(getContext()).competitionDao().GetCompetitionById(competitionId);
                competition.FinishCompetition(getContext());
                ((DatabaseUpdateListener)getActivity()).OnDBUpdate();
            }
        });

        return view;
    }


}
