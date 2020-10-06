package com.example.listapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;


public class CategoryManager {

    private Context context;

    public CategoryManager(Context c)
    {
        context=c;
    }

    public void saveCategory(Categories category)
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        HashSet itemsHashSet=new HashSet(category.getItems());

        editor.putStringSet(category.getName(), itemsHashSet);

        editor.apply();
    }

    public void deleteCategory(Categories category)
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.remove(category.getName());

        editor.apply();
    }

    public ArrayList<Categories> getCategories()
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);

        Map<String, ?> data=sharedPreferences.getAll();

        ArrayList<Categories> returnData=new ArrayList<>();

        for (Map.Entry<String,?>entry:data.entrySet())
        {
            Categories category=new Categories(entry.getKey(),new ArrayList<String>((HashSet)entry.getValue()));
            returnData.add(category);
        }
        return returnData;
    }

    public boolean exists(Categories c)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPreferences.contains(c.getName()))
            return true;
        else
            return false;
    }
}
