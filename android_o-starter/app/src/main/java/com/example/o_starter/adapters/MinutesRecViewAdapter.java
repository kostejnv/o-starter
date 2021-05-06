package com.example.o_starter.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MinutesRzcViewAdapter {




    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView timeTextView;
        private ImageView arrowImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
