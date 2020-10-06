package com.example.listapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnCategoryInteractionListener {


    static final String IntentKey="Category_key";
    static final int Main_requestCode=1281;
    FloatingActionButton remove;


    CategoryFragment mCategoryFragment;
    boolean isTablet=false;
    CategoryItemFragment mCategoryItemFragment;
    FloatingActionButton fab;

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        mCategoryFragment=(CategoryFragment)getSupportFragmentManager().findFragmentById(R.id.CategoryFragment);
        frameLayout=findViewById(R.id.CategoryItemsFragmentContainer);

        isTablet=frameLayout!=null;

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toaster("FAB IS TAPPED");
                displayCreateCategory();
            }
        });

        remove=findViewById(R.id.fab2);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toaster("REMOVE IS TAPPED");
                displayDestroyCategory();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Toaster(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    private void displayCreateCategory()
    {
        String alertTitle = getString(R.string.createCategory);
        String alertPosTitle=getString(R.string.createCategoryPosBtn) ;
        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(MainActivity.this);
        final EditText categoryEditor = new EditText(this);
        categoryEditor.setInputType(InputType.TYPE_CLASS_TEXT);

        alertBuilder.setTitle(alertTitle);
        alertBuilder.setView(categoryEditor);

        alertBuilder.setPositiveButton(alertPosTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Categories newCategory=new Categories(categoryEditor.getText().toString(),new ArrayList<String>());
                mCategoryFragment.addCategoryToManager(newCategory);

                dialogInterface.dismiss();
                displayCategory(newCategory);
            }
        });
        alertBuilder.create().show();
    }

    private void displayCategory(Categories category)
    {
        if(!isTablet) {
            Intent categoryItemsIntent = new Intent(this, CategoryActivityItems.class);
            categoryItemsIntent.putExtra(IntentKey, category);

            startActivityForResult(categoryItemsIntent, Main_requestCode);
        }
        else
        {
           if(mCategoryItemFragment!=null) {
               getSupportFragmentManager().beginTransaction().remove(mCategoryItemFragment).commit();
                mCategoryItemFragment=null;
           }
            setTitle(category.getName());
            mCategoryItemFragment=CategoryItemFragment.newInstance(category);
            if(mCategoryItemFragment!=null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.CategoryItemsFragmentContainer,mCategoryItemFragment).addToBackStack(null).commit();
            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    displayCreateCategoryItemDialog();
                }
            });
        }

    }

    private void displayCreateCategoryItemDialog() {
        final EditText editText=new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        new AlertDialog.Builder(this)
                .setTitle("ENTER ITEM NAME")
                .setView(editText)
                .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item=editText.getText().toString();
                        mCategoryItemFragment.addItemToCategory(item);
                        dialogInterface.dismiss();
                    }
                }).create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Main_requestCode && resultCode== Activity.RESULT_OK)
        {
            if(data!=null)
            {
                mCategoryFragment.saveCategory((Categories)data.getSerializableExtra(IntentKey));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }





    void displayDestroyCategory()
    {
        String alertTitle = getString(R.string.category_Destroy);
        String alertPosTitle=getString(R.string.category_DestroyPOSBtn) ;
        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(MainActivity.this);
        final EditText categoryEditor = new EditText(this);
        categoryEditor.setInputType(InputType.TYPE_CLASS_TEXT);

        alertBuilder.setTitle(alertTitle);
        alertBuilder.setView(categoryEditor);

        alertBuilder.setPositiveButton(alertPosTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Categories newCategory=new Categories(categoryEditor.getText().toString(),new ArrayList<String>());

                if(mCategoryFragment.exists(newCategory)==false)
                    Toaster("CATEGORY DOESN'T EXIST");
                else {
                    mCategoryFragment.deleteCategory(newCategory);

                }
            }
        });
        alertBuilder.create().show();
    }

    @Override
    public void CategoryIsTapped(Categories category) {
        displayCategory(category);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTitle(R.string.app_name);
        if(mCategoryItemFragment.category!=null)
        {
            mCategoryFragment.mCategoryManager.saveCategory(mCategoryItemFragment.category);
        }
        if(mCategoryItemFragment!=null)
        {
            getSupportFragmentManager().beginTransaction().remove(mCategoryItemFragment).commit();
            mCategoryItemFragment=null;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCreateCategory();
            }
        });
    }
}