package com.campstore.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.campstore.babyneeds.model.Item;
import com.campstore.babyneeds.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final Context context;
    private final String TAG = "DatabaseHandler";

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DATABSE_NAME, null, Constants.DATABASE_VERSION);

        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BABY_TABLE = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_ITEM_NAME + " TEXT,"
                + Constants.KEY_ITEM_QTY + " INTEGER,"
                + Constants.KEY_ITEM_SIZE + " INTEGER,"
                + Constants.KEY_ITEM_COLOR + " TEXT,"
                + Constants.KEY_ITEM_DATE + " LONG);";

              db.execSQL(CREATE_BABY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);

            onCreate(db);
    }


    public void addItem(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.KEY_ITEM_NAME, item.getItemName());
        values.put(Constants.KEY_ITEM_QTY, item.getItemQty());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Constants.KEY_ITEM_COLOR, item.getItemColor());
        values.put(Constants.KEY_ITEM_DATE, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);

        Log.d(TAG, "added Item: "+ values);


    }


    //Get a item

    public Item getItem(int id){

        Item itemToReturn = null ;

        SQLiteDatabase db = this.getReadableDatabase();

      Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID,
                Constants.KEY_ITEM_NAME, Constants.KEY_ITEM_QTY,
                Constants.KEY_ITEM_COLOR, Constants.KEY_ITEM_SIZE,
                Constants.KEY_ITEM_DATE}, String.valueOf(new String[]{
                Constants.KEY_ID + "=?", String.valueOf(id)}),
                null, null, null, null);


      if(cursor != null){

          cursor.moveToFirst();

          Item item = new Item();

          item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
          item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));
          item.setItemQty(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_QTY)));
          item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_COLOR)));

          //Format Date
          DateFormat dateFormat = DateFormat.getDateInstance();

        String dateformatted =  dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ITEM_DATE))).getTime()); // Apr 18, 2020

        item.setItemDate(dateformatted);

        itemToReturn = item;
      }
        return itemToReturn;

    }

    //Get all items

    public List<Item> getAllItems(){
       List<Item> allItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID,
                Constants.KEY_ITEM_NAME, Constants.KEY_ITEM_QTY,
                Constants.KEY_ITEM_COLOR, Constants.KEY_ITEM_SIZE,
                Constants.KEY_ITEM_DATE },null, null, null,null, Constants.KEY_ITEM_DATE + " DESC");

        if(cursor.moveToFirst()){

            do {

                Item item = new Item();

                item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));
                item.setItemQty(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_QTY)));
                item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_COLOR)));

                //Format Date
                DateFormat dateFormat = DateFormat.getDateInstance();

                String dateformatted =  dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ITEM_DATE))).getTime()); // Apr 18, 2020

                item.setItemDate(dateformatted);

                allItems.add(item);


            }  while (cursor.moveToNext());
        }


        return  allItems;


    }

    //Update item

    public void updateItem(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.KEY_ITEM_NAME, item.getItemName());
        values.put(Constants.KEY_ITEM_QTY, item.getItemQty());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Constants.KEY_ITEM_COLOR, item.getItemColor());
        values.put(Constants.KEY_ITEM_DATE, java.lang.System.currentTimeMillis());


        db.update(Constants.TABLE_NAME, values,Constants.KEY_ID + "=?", new String[]{
                 String.valueOf(item.getId())
        });

        Log.d(TAG, "Item Updated "+ values);
    }

    //Delete Item

    public  void deleteItem(int id){

        SQLiteDatabase db  = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?", new String[]{
                 String.valueOf(id)
        });
        db.close();
    }

    //Get all items count

    public int getItemsCount(){
        SQLiteDatabase db = this.getReadableDatabase();

       Cursor cursor = db.rawQuery("SELECT * FROM "+ Constants.TABLE_NAME, null);

       return  cursor.getCount();

    }

}
