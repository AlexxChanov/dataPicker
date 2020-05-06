package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DaysViewHolder> {

    private static int viewHolderCount;

   private int numberItem;
    private ArrayList<String> monthsItem;
    private String[] yearsItem;


    public DateAdapter(String[] numberOfItems){
        yearsItem = numberOfItems;
    }

    public DateAdapter(ArrayList<String> months){
        monthsItem = months;
    }
    public DateAdapter(int item){
        numberItem = item;
    }


    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem= R.layout.row_date;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent,false);

        DaysViewHolder viewHolder = new DaysViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {
        if(monthsItem==null&&yearsItem==null){ holder.bind(position);}
        else if (monthsItem==null){holder.listItemDateView.setText(String.valueOf(yearsItem[position]));}
        else holder.listItemDateView.setText(monthsItem.get(position));
        List<Integer> items = new ArrayList<>();
        items.add(position);

    }

    @Override
    public int getItemCount() {
        if(monthsItem==null&&yearsItem==null){
            return numberItem;
        }
        else if(monthsItem==null){
            return yearsItem.length;
        }
        else return monthsItem.size();
    }

    class DaysViewHolder extends RecyclerView.ViewHolder{

        TextView listItemDateView;


        public DaysViewHolder(View itemView) {
            super(itemView);

            listItemDateView = itemView.findViewById(R.id.currentDate);

        }
        void bind(int listIndex){
            listItemDateView.setText(String.valueOf(listIndex));
        }

    }
}
