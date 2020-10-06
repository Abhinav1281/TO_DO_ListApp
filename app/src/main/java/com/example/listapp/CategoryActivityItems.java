package com.example.listapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryActivityItems extends AppCompatActivity implements ItemsRecyclerAdapter.ItemIsClicked {

    FloatingActionButton addItems;
    RecyclerView itemsRecyclerView;
    Categories category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        category=(Categories)getIntent().getSerializableExtra(MainActivity.IntentKey);

        setTitle(category.getName());

        addItems=findViewById(R.id.addItemButton);
        itemsRecyclerView=findViewById(R.id.ItemsRecyclerView);
        itemsRecyclerView.setAdapter(new ItemsRecyclerAdapter(category,CategoryActivityItems.this));
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CategoryActivityItems.this,"ITEM CAN BE ADDED",Toast.LENGTH_SHORT).show();
                displayItemCreation();
            }
        });
    }

    void displayItemCreation()
    {
        final EditText itemCreation=new EditText(this);
        itemCreation.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder itemCreationAlert=new AlertDialog.Builder(this);
        itemCreationAlert.setTitle(R.string.ItemDialogTitle);
        itemCreationAlert.setView(itemCreation);
        itemCreationAlert.setPositiveButton(R.string.createCategoryPosBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String item=itemCreation.getText().toString();
                category.getItems().add(item);
                ItemsRecyclerAdapter itemsRecyclerAdapter=(ItemsRecyclerAdapter)itemsRecyclerView.getAdapter();
                itemsRecyclerAdapter.notifyItemInserted(category.getItems().size()-1);
                dialogInterface.dismiss();
            }
        });
        itemCreationAlert.create().show();
    }

    @Override
    public void onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putSerializable(MainActivity.IntentKey,category);
        Intent intent=new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent);
        super.onBackPressed();
    }

    @Override
    public void ItemClicked(String item) {
        final String itemDelete=item;
        final int itemPos=category.getItems().indexOf(item);
        AlertDialog.Builder itemDeletionAlert=new AlertDialog.Builder(this);
        itemDeletionAlert.setTitle(R.string.deletionItemAlert);
        itemDeletionAlert.setPositiveButton(getString(R.string.deletionAlertYes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                category.getItems().remove(itemDelete);
                ItemsRecyclerAdapter itemsRecyclerAdapter=(ItemsRecyclerAdapter)itemsRecyclerView.getAdapter();
                itemsRecyclerAdapter.notifyItemRemoved(itemPos);
                dialogInterface.dismiss();
            }
        });
        itemDeletionAlert.create().show();
    }
}