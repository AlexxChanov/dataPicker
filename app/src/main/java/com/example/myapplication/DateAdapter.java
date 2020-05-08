package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
    List<Integer> items = new ArrayList<>();
    int selectedPosition=-1;
    private int numberItem;
    private ArrayList<String> monthsItem;
    private String[] yearsItem;
    private int index=3;


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
//            items.add(position);
//            if(items.size()>7) {
//                items.remove(0);
//                index++;
//            }
            holder.listItemDateView.setText(String.valueOf(yearsItem[position]));



//             if (items.size()==7){
//                if(position==items.get(6)-2)
//                    holder.listItemDateView.setTextColor(Color.parseColor("#6200EE"));
//            }
//

        }
        else {holder.listItemDateView.setText(monthsItem.get(position));
//            items.add(position);
//            if (items.size()==7){
//                if(position==items.get(6)-2)
//                    holder.listItemDateView.setTextColor(Color.parseColor("#6200EE"));
//            }
//            else if(items.size()>7) {
//                items.remove(0);
//            }
        }

       //  0 1 2 3 4
        //{1,2,3,4,5}
        //{3,4,5,6,7}




//        List<Integer> items = new ArrayList<>();
//        items.add(position);
//        List<Integer> pos = new ArrayList<>();
//        pos.add(position);
//
//        if(selectedPosition==position)
//            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
//        else
//            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
//
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedPosition=position;
//                notifyDataSetChanged();
//
//            }
//       });

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
