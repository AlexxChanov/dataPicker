package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView daysList,monthsList,yearsList;
    private DateAdapter dateAdapter1,dateAdapter2,dateAdapter3;
    private ArrayList<String> months ;
    Button date , time;
    private int[] years = new int[30];
    private int[] days = new int[32];
    private int firstYear=2000;
    private int firstDay=1;
    private int firstMinute=1;
    private int[] hours= new int[24];
    private int[] minutes= new int[61];
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date=findViewById(R.id.dateBtn);
        time=findViewById(R.id.timeBtn);

        daysList = findViewById(R.id.recyclerViewDays);
        monthsList = findViewById(R.id.recyclerViewMonths);
        yearsList = findViewById(R.id.recyclerViewYears);
        textView = findViewById(R.id.currentDate);

        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstYear=2000;
                firstDay=1;
                firstMinute=1;
                DecimalFormat twodigit = new DecimalFormat("00");
                yearsList.setVisibility(View.GONE);
                for(int n=0;n<hours.length;n++){
                    hours[n]=firstDay++;
                }
                for(int n=0;n<61;n++){
                    minutes[n]=firstMinute++;
                }


                daysList.setLayoutManager(layoutManager1);
                monthsList.setLayoutManager(layoutManager2);


                daysList.setHasFixedSize(true);//заранее знаем размер списка и эффективность повысится
                monthsList.setHasFixedSize(true);

                dateAdapter1 = new DateAdapter(hours);
                dateAdapter2 = new DateAdapter(minutes);

                daysList.setAdapter(dateAdapter1);
                monthsList.setAdapter(dateAdapter2);

              final  SnapHelper snapHelper1 = new LinearSnapHelper();
              final  SnapHelper snapHelper2 = new LinearSnapHelper();

                snapHelper1.attachToRecyclerView(daysList);
                daysList.setOnFlingListener(null);
                snapHelper2.attachToRecyclerView(monthsList);
                monthsList.setOnFlingListener(null);

                daysList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper1.findSnapView(layoutManager1);
                            int pos = layoutManager1.getPosition(centerView);
                            Log.e("Snapped Item Position:",""+pos);

                        }
                    }
                });
                monthsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper2.findSnapView(layoutManager2);
                            int pos = layoutManager2.getPosition(centerView);
                            Log.e("Snapped Item Position:",""+pos);

                        }
                    }
                });

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstYear=2000;
                firstDay=1;
                yearsList.setVisibility(View.VISIBLE);
                for(int n=0;n<years.length;n++){
                    years[n]=firstYear++;
                }
                for(int n=0;n<days.length;n++){
                    days[n]=firstDay++;
                }

                months = new ArrayList<>();
                months.add("янв.");
                months.add("фев.");
                months.add("март.");
                months.add("апрл.");
                months.add("май");
                months.add("июнь");
                months.add("июль");
                months.add("авг.");
                months.add("сент.");
                months.add("окт.");
                months.add("нояб.");
                months.add("дек.");
                months.add("янв.");
                months.add("фев.");
                months.add("март.");


                daysList.setLayoutManager(layoutManager1);
                monthsList.setLayoutManager(layoutManager2);
                yearsList.setLayoutManager(layoutManager3);




                daysList.setHasFixedSize(true);//заранее знаем размер списка и эффективность повысится
                monthsList.setHasFixedSize(true);
                yearsList.setHasFixedSize(true);

                dateAdapter1 = new DateAdapter(days);
                dateAdapter2 = new DateAdapter(months);
                dateAdapter3 = new DateAdapter(years);
                daysList.setAdapter(dateAdapter1);
                monthsList.setAdapter(dateAdapter2);
                yearsList.setAdapter(dateAdapter3);

               final SnapHelper snapHelper1 = new LinearSnapHelper();
               final SnapHelper snapHelper2 = new LinearSnapHelper();
               final SnapHelper snapHelper3 = new LinearSnapHelper();

                snapHelper1.attachToRecyclerView(daysList);
                daysList.setOnFlingListener(null);
                snapHelper2.attachToRecyclerView(monthsList);
                monthsList.setOnFlingListener(null);
                snapHelper3.attachToRecyclerView(yearsList);
                yearsList.setOnFlingListener(null);
                daysList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper1.findSnapView(layoutManager1);
                            int pos = layoutManager1.getPosition(centerView);
                            Log.e("Snapped Item Position:",""+pos);

                        }
                    }
                });
                monthsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper2.findSnapView(layoutManager2);
                            int pos = layoutManager2.getPosition(centerView);
                            Log.e("Snapped Item Position:",""+pos);

                        }
                    }
                });
                yearsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper3.findSnapView(layoutManager3);
                            int pos = layoutManager3.getPosition(centerView);
                            Log.e("Snapped Item Position:",""+pos);

                        }
                    }
                });
            }
        });

        for(int n=0;n<years.length;n++){
            years[n]=firstYear++;
        }
        for(int n=0;n<days.length;n++){
            days[n]=firstDay++;
        }



        months = new ArrayList<>();
        months.add("янв.");
        months.add("фев.");
        months.add("март.");
        months.add("апрл.");
        months.add("май");
        months.add("июнь");
        months.add("июль");
        months.add("авг.");
        months.add("сент.");
        months.add("окт.");
        months.add("нояб.");
        months.add("дек.");
        months.add("янв.");
        months.add("фев.");
        months.add("март.");


        daysList.setLayoutManager(layoutManager1);
        monthsList.setLayoutManager(layoutManager2);
        yearsList.setLayoutManager(layoutManager3);




        daysList.setHasFixedSize(true);//заранее знаем размер списка и эффективность повысится
        monthsList.setHasFixedSize(true);
        yearsList.setHasFixedSize(true);

        dateAdapter1 = new DateAdapter(days);
        dateAdapter2 = new DateAdapter(months);
        dateAdapter3 = new DateAdapter(years);
        daysList.setAdapter(dateAdapter1);
        monthsList.setAdapter(dateAdapter2);
        yearsList.setAdapter(dateAdapter3);

      final  SnapHelper snapHelper1 = new LinearSnapHelper();
       final SnapHelper snapHelper2 = new LinearSnapHelper();
      final  SnapHelper snapHelper3 = new LinearSnapHelper();

        snapHelper1.attachToRecyclerView(daysList);
        daysList.setOnFlingListener(null);
        snapHelper2.attachToRecyclerView(monthsList);
        monthsList.setOnFlingListener(null);
        snapHelper3.attachToRecyclerView(yearsList);
        yearsList.setOnFlingListener(null);

        daysList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper1.findSnapView(layoutManager1);
                    int pos = layoutManager1.getPosition(centerView);
                    Log.e("Snapped Item Position:",""+pos);

                }
            }
        });
        monthsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper2.findSnapView(layoutManager2);
                    int pos = layoutManager2.getPosition(centerView);
                    Log.e("Snapped Item Position:",""+pos);

                }
            }
        });
        yearsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper3.findSnapView(layoutManager3);
                    int pos = layoutManager3.getPosition(centerView);
                    Log.e("Snapped Item Position:",""+pos);

                }
            }
        });

    }
}
