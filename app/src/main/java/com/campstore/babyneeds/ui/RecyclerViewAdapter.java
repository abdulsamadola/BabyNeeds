package com.campstore.babyneeds.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.campstore.babyneeds.ListActivity;
import com.campstore.babyneeds.MainActivity;
import com.campstore.babyneeds.R;
import com.campstore.babyneeds.data.DatabaseHandler;
import com.campstore.babyneeds.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.ViewHolder> {

    private  Context context;
    private List<Item> allItems;

    public RecyclerViewAdapter(Context context, List<Item> allItems) {
        this.context = context;
        this.allItems = allItems;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Item item = allItems.get(position);

        holder.item_name.setText(item.getItemName());
        holder.item_qty.setText("Qty: " + item.getItemQty());
        holder.item_size.setText("Size: "+ item.getItemSize());
        holder.item_color.setText("Color: "+ item.getItemColor());
        holder.item_date.setText("Date: "+item.getItemDate());


    }


    @Override
    public int getItemCount() {
        return allItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item_name;
        public TextView item_color;
        public TextView item_size;
        public TextView item_qty;
        public TextView item_date;
        public Button edit_button;
        public Button delete_button;
        public DatabaseHandler databaseHandler;
        private AlertDialog.Builder builder;
        private AlertDialog alertDialog;



        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            item_name = itemView.findViewById(R.id.item_name);
            item_qty = itemView.findViewById(R.id.item_qty);
            item_size = itemView.findViewById(R.id.item_size);
            item_color = itemView.findViewById(R.id.item_color);
            item_date = itemView.findViewById(R.id.item_date);
            edit_button = itemView.findViewById(R.id.edit_button);
            delete_button = itemView.findViewById(R.id.delete_button);

            databaseHandler = new DatabaseHandler(ctx);

            edit_button.setOnClickListener(this);
            delete_button.setOnClickListener(this);

            builder = new AlertDialog.Builder(context);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_button:

                    updateItem(allItems.get(getAdapterPosition()));

                    break;

                case R.id.delete_button:

                    confirmDelete(getAdapterPosition());

                    break;

            }

        }

        private void updateItem(final Item newItem) {
            View view = LayoutInflater.from(context).inflate(R.layout.popup, null);
            final EditText itemName;
            final EditText itemQty;
            final EditText itemSize;
            final EditText itemColor;
            Button itemSave;
            TextView title;


            final Item data = databaseHandler.getItem(allItems.get(getAdapterPosition()).getId());


            title = view.findViewById(R.id.titleView);
            itemName = view.findViewById(R.id.itemName);
            itemQty = view.findViewById(R.id.itemQty);
            itemSize = view.findViewById(R.id.itemSize);
            itemColor = view.findViewById(R.id.itemColor);
            itemSave = view.findViewById(R.id.itemSave);

            title.setText("Update Item");
            itemName.setText(data.getItemName());
            itemQty.setText(String.valueOf(data.getItemQty()));
            itemSize.setText(String.valueOf(data.getItemSize()));
            itemColor.setText(data.getItemColor());


            itemSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String newItemName;
                    String newItemQty;
                    String newItemSize;
                    String newItemColor;

                    newItemName = itemName.getText().toString().trim();
                    newItemQty = itemQty.getText().toString().trim();
                    newItemSize = itemSize.getText().toString().trim();
                    newItemColor = itemColor.getText().toString().trim();

                    if(!newItemName.isEmpty()
                            && !newItemColor.isEmpty()
                            && !newItemSize.isEmpty()
                            && !newItemQty.isEmpty()
                    ) {
                        newItem.setItemName(newItemName);
                        newItem.setItemQty(Integer.parseInt(newItemQty));
                        newItem.setItemColor(newItemColor);
                        newItem.setItemSize(Integer.parseInt(newItemSize));
                        databaseHandler.updateItem(newItem);
                        notifyItemChanged(getAdapterPosition(), newItem);
                        Snackbar.make(v, "Item has been updated!", Snackbar.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                alertDialog.dismiss();

                            }
                        }, 1000);
                    }else {
                        Snackbar.make(v, "Empty fields are not allowed!", Snackbar.LENGTH_SHORT)
                                .show();
                    }

                }
            });




            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();
        }

        private void confirmDelete(final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.confirmation, null);
        Button confirmed = view.findViewById(R.id.confirm_yes);
        Button notconfirmed = view.findViewById(R.id.confirm_no);

        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Delete and Dismiss
                databaseHandler.deleteItem(allItems.get(position).getId());
                allItems.remove(position);
                alertDialog.dismiss();
                notifyItemRemoved(position);
            }
        });

        notconfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dismiss view
              alertDialog.dismiss();
            }
        });

         builder.setView(view);
         alertDialog = builder.create();
         alertDialog.show();

        }
    }
}
