package com.campstore.babyneeds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.campstore.babyneeds.data.DatabaseHandler;
import com.campstore.babyneeds.model.Item;
import com.campstore.babyneeds.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> allItems = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fab;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText itemName;
    private EditText itemQty;
    private EditText itemSize;
    private EditText itemColor;
    private Button itemSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        databaseHandler = new DatabaseHandler(this);
        fab = findViewById(R.id.fab);
        if(databaseHandler.getItemsCount() > 0) {
            for (Item singleItem : databaseHandler.getAllItems()) {
                Log.d("Result", "Single data: "+ singleItem);
                allItems.add(singleItem);
            }
        }else {
            startActivity(new Intent(ListActivity.this, MainActivity.class));
            finish();
        }

         recyclerViewAdapter = new RecyclerViewAdapter(this, allItems);
         recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
          recyclerView.setAdapter(recyclerViewAdapter);
          recyclerViewAdapter.notifyDataSetChanged();

          fab.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  createDialogPopUp();

              }
          });


    }

    private void createDialogPopUp(){
        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup, null);

        itemName = view.findViewById(R.id.itemName);
        itemQty = view.findViewById(R.id.itemQty);
        itemSize = view.findViewById(R.id.itemSize);
        itemColor = view.findViewById(R.id.itemColor);
        itemSave = view.findViewById(R.id.itemSave);

        builder.setView(view);

        alertDialog = builder.create();

        alertDialog.show();

        itemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                saveItem(v);
            }
        });


    }

    private void saveItem(final View v) {
        String newItemSize;
        String newItemColor;
        String newItemQty;
        String newItemName;

        newItemName = itemName.getText().toString().trim();
        newItemQty = itemQty.getText().toString().trim();
        newItemSize = itemSize.getText().toString().trim();
        newItemColor = itemColor.getText().toString().trim();

        if(!newItemName.isEmpty()
                && !newItemColor.isEmpty()
                && !newItemSize.isEmpty()
                && !newItemQty.isEmpty()
        ) {
            Item item = new Item();
            item.setItemName(newItemName);
            item.setItemQty(Integer.parseInt(newItemQty));
            item.setItemColor(newItemColor);
            item.setItemSize(Integer.parseInt(newItemSize));
            databaseHandler.addItem(item);
            Snackbar.make(v, "Item has been added!", Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    alertDialog.dismiss();
                    startActivity(new Intent(ListActivity.this, ListActivity.class));
                     finish();
                }
            }, 1000);
        }else {
            Snackbar.make(v, "Empty fields are not allowed!", Snackbar.LENGTH_SHORT)
                    .show();
        }

    }
}
