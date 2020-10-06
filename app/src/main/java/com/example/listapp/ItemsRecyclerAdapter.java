package com.example.listapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    Categories category;
    ItemIsClicked itemClickInterface;
    public ItemsRecyclerAdapter(Categories category,ItemIsClicked itemClickInterface)
    {
        this.category=category;
        this.itemClickInterface=itemClickInterface;
    }

    interface ItemIsClicked
    {
        void ItemClicked(String item);
    }
    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.itemviewholder,parent,false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, final int position) {

        holder.itemName.setText(String.valueOf(category.getItems().get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickInterface.ItemClicked(category.getItems().get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return category.getItems().size();
    }
    public void setCategory(Categories category)
    {
        this.category=category;
    }
}
