package com.example.listapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CategoryItemFragment extends Fragment implements ItemsRecyclerAdapter.ItemIsClicked {

    RecyclerView itemsRecyclerView;
    Categories category;
    static String Category_Args="FRAGMENT_KEY";


    public CategoryItemFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CategoryItemFragment newInstance(Categories category) {
        CategoryItemFragment categoryItemFragment =new CategoryItemFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Category_Args,category);
        categoryItemFragment.setArguments(bundle);
        return categoryItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = (Categories) getArguments().getSerializable(Category_Args);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category_item, container, false);
        if(view!=null) {
            itemsRecyclerView = view.findViewById(R.id.ItemsRecyclerView);
            itemsRecyclerView.setAdapter(new ItemsRecyclerAdapter(category,this));
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else
            throw new RuntimeException("VIEW IS NOT CORRECTLY INITIALISED");
        return view;
    }

    @Override
    public void ItemClicked(final String item) {
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setTitle(R.string.deletionItemAlert);
        alert.setCancelable(true);
        alert.setPositiveButton(R.string.deletionAlertYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                category.getItems().remove(item);
                ItemsRecyclerAdapter itemsRecyclerAdapter=(ItemsRecyclerAdapter)itemsRecyclerView.getAdapter();
                itemsRecyclerAdapter.setCategory(category);
                itemsRecyclerAdapter.notifyDataSetChanged();
            }
        });
        alert.create().show();

    }

    public void addItemToCategory(String item)
    {
        category.getItems().add(item);
        ItemsRecyclerAdapter itemsRecyclerAdapter=(ItemsRecyclerAdapter)itemsRecyclerView.getAdapter();
        itemsRecyclerAdapter.setCategory(category);
        itemsRecyclerAdapter.notifyDataSetChanged();
    }
}