package com.example.listapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class CategoryFragment extends Fragment implements RecyclerAdapter.CategoryClickInterface {

    RecyclerView categoryRecyclerview;
    CategoryManager mCategoryManager;

    @Override
    public void CategoryIsClicked(Categories category) {
        ListenerObject.CategoryIsTapped(category);
    }



    interface OnCategoryInteractionListener{
       void CategoryIsTapped(Categories category);
   }
    public CategoryFragment() {
        // Required empty public constructor
    }

    OnCategoryInteractionListener ListenerObject;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnCategoryInteractionListener)
        {
            mCategoryManager=new CategoryManager(context);
            ListenerObject=(OnCategoryInteractionListener)context;

        }
        else
        {
            throw new RuntimeException("THE CONTEXT DOESN'T IMPLEMENT OnCategoryInteractionListener Interface");
        }
    }

    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance() {
      return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Categories> categories=mCategoryManager.getCategories();
        if(getView()!=null) {
            categoryRecyclerview = getView().findViewById(R.id.categoryList);
            categoryRecyclerview.setAdapter(new RecyclerAdapter(categories, this));
            categoryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onDetach() {
        ListenerObject=null;
        super.onDetach();
    }

    void addCategoryToManager(Categories category)
    {
        mCategoryManager.saveCategory(category);
        RecyclerAdapter recyclerAdapter=(RecyclerAdapter)categoryRecyclerview.getAdapter();
        recyclerAdapter.addCategory(category);
    }

    void saveCategory(Categories category)
    {
        mCategoryManager.saveCategory(category);
        updateRecyclerView();
    }

    private void updateRecyclerView() {

        ArrayList<Categories> categories=mCategoryManager.getCategories();
        categoryRecyclerview.setAdapter(new RecyclerAdapter(categories,this));

    }
    void deleteCategory(Categories category)
    {
        mCategoryManager.deleteCategory(category);
        updateRecyclerView();
    }
    boolean exists(Categories category)
    {
        if(mCategoryManager.exists(category)) return true;
        else return false;
    }
}