package com.example.listapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Categories implements Serializable {
    private String name;
    private ArrayList<String> items= new ArrayList<>();

    public Categories(String CategoryName, ArrayList<String> modifiedList)
    {
        name=CategoryName;
        items=modifiedList;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<String> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
}
