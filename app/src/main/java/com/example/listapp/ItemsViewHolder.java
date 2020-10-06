package com.example.listapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsViewHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
    public ItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName=itemView.findViewById(R.id.ItemName);

    }

    public void setItemName(TextView itemName) {
        this.itemName = itemName;
    }
}
