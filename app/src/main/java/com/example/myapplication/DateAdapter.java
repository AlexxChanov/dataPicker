package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DaysViewHolder> {
    int centerEl = 2;
    private int numberItem;
    private ArrayList<String> monthsItem;
    private String[] yearsItem;
    private int index = 3;


    public DateAdapter(String[] numberOfItems) {
        yearsItem = numberOfItems;
    }

    public DateAdapter(ArrayList<String> months) {
        monthsItem = months;
    }


    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.row_date;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        DaysViewHolder viewHolder = new DaysViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, final int position) {

        if (monthsItem == null && yearsItem == null) {
            holder.bind(position);
        } else if (monthsItem == null) {
            Log.d("items", String.valueOf(position));


            holder.listItemDateView.setText(String.valueOf(yearsItem[position]));
        } else {
            holder.listItemDateView.setText(monthsItem.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (monthsItem == null && yearsItem == null) {
            return numberItem;
        } else if (monthsItem == null) {
            return yearsItem.length;
        } else return monthsItem.size();
    }

    class DaysViewHolder extends RecyclerView.ViewHolder {

        TextView listItemDateView;


        public DaysViewHolder(View itemView) {
            super(itemView);

            listItemDateView = itemView.findViewById(R.id.currentDate);


        }

        void bind(int listIndex) {
            listItemDateView.setText(String.valueOf(listIndex));
        }

    }

}
