package com.example.listapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    interface CategoryClickInterface
    {
        void CategoryIsClicked(Categories category);
    }

   private ArrayList<Categories> categories;
    private CategoryClickInterface categoryClickListener;

    public RecyclerAdapter(ArrayList<Categories> categories,CategoryClickInterface categoryClickInterface)
    {
        this.categories = categories;
        this.categoryClickListener=categoryClickInterface;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.category_view, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.getTxtNumber().setText(position+1+"");
        holder.getTxtName().setText(categories.get(position).getName()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickListener.CategoryIsClicked(categories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Categories c)
    {
        categories.add(c);
        notifyItemInserted(categories.size()-1);
    }

    public void removeCategory(Categories c)
    {
        categories.remove(c);
        notifyItemRemoved(categories.size()-1);
    }
}
