package com.campstore.babyneeds;

import android.content.Intent;
import android.os.Bundle;

import com.campstore.babyneeds.data.DatabaseHandler;
import com.campstore.babyneeds.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText itemName;
    private EditText itemQty;
    private EditText itemSize;
    private EditText itemColor;
    private Button itemSave;
    private DatabaseHandler databaseHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHandler = new DatabaseHandler(this);
          simpleHack();
        for(Item newItem : databaseHandler.getAllItems()){

            Log.d("All Items from DB", "onCreate: "+ newItem.getItemName());
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createDialogPopUp();


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void simpleHack() {

        if(databaseHandler.getItemsCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }

    private void createDialogPopUp() {
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
                        startActivity(new Intent(MainActivity.this, ListActivity.class));

                    }
                }, 1000);
            }else {
                Snackbar.make(v, "Empty fields are not allowed!", Snackbar.LENGTH_SHORT)
                        .show();
            }

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
             Toast.makeText(this,"Setting Enabled", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
