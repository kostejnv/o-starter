package com.example.o_starter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.o_starter.R;
import com.example.o_starter.database.StartlistsDatabase;
import com.example.o_starter.database.entities.ChangedRunner;
import com.example.o_starter.database.entities.Runner;

import java.util.List;

/**
 * Recycler view for each change for given competiton in {@link com.example.o_starter.activities.ViewChangesActivity ViewChangesActivity}
 */
public class ChangesRecViewAdapter extends RecyclerView.Adapter<ChangesRecViewAdapter.ViewHolder> {
    private final int competitionId;
    private final List<ChangedRunner> changes;
    private final Context context;

    /**
     *
     * @param context context of parent
     */
    public ChangesRecViewAdapter(int competitionId, Context context) {
        this.competitionId = competitionId;
        this.changes = StartlistsDatabase.getInstance(context).changedRunnerDao().GetCompetitionChanges(competitionId);
        this.context = context;
    }

    /**
     * Self-documenting
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * setting all components of view holder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChangedRunner currentChange = changes.get(position);
        Runner newRunner = StartlistsDatabase.getInstance(context).runnerDao().getById(currentChange.getRunnerId());
        holder.oldRegTextView.setText(currentChange.getOldRegistrationId());
        holder.oldNameTextView.setText(currentChange.getOldSurname()+" "+currentChange.getOldName());
        holder.newRegTextView.setText(newRunner.getRegistrationId());
        holder.newNameTextView.setText(newRunner.getSurname()+" "+newRunner.getName());
        holder.newSITextView.setText(String.valueOf(newRunner.getCardNumber()));
        holder.newStartNumberTextView.setText(String.valueOf(newRunner.getStartNumber()));
    }

    /**
     *
     * @return number of changes for given competition
     */
    @Override
    public int getItemCount() {
        return changes.size();
    }

    /**
     * Holder of views for each change
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView oldRegTextView;
        private TextView oldNameTextView;
        private TextView newRegTextView;
        private TextView newNameTextView;
        private TextView newSITextView;
        private TextView newStartNumberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitializeComponents(itemView);
        }

        private void InitializeComponents(View itemView) {
            oldRegTextView = itemView.findViewById(R.id.old_reg_textView);
            oldNameTextView = itemView.findViewById(R.id.old_name_textView);
            newRegTextView = itemView.findViewById(R.id.new_reg_textView);
            newNameTextView = itemView.findViewById(R.id.new_name_textView);
            newSITextView = itemView.findViewById(R.id.new_SI_textView);
            newStartNumberTextView = itemView.findViewById(R.id.new_start_number_textView);
        }
    }
}
