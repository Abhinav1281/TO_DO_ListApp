package com.example.listapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView txtNumber, txtName;

    public RecyclerViewHolder(View view) {
        super(view);

        txtNumber=view.findViewById(R.id.category_number_text);
        txtName=view.findViewById(R.id.category_name_id);
    }

    public TextView getTxtNumber() {
        return txtNumber;
    }

    public TextView getTxtName() {
        return txtName;
    }
}
