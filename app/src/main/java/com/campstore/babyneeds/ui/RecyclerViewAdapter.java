package com.campstore.babyneeds.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.campstore.babyneeds.R;
import com.campstore.babyneeds.model.Item;

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

            edit_button.setOnClickListener(this);
            delete_button.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.edit_button:

                    break;

                case R.id.delete_button:

                    break;

            }

        }
    }
}
