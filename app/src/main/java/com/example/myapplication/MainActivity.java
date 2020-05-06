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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    boolean flag = true;
    private RecyclerView daysList, monthsList, yearsList,hoursList,minutesList;
    private DateAdapter dateAdapter1, dateAdapter2, dateAdapter3;
    private ArrayList<String> months;
    Button date, time, save;
    private String[] years = new String[30];
    private String[] days = new String[32];
    private int firstYear = 2000;
    private int firstDay = 1;
    private int firstMinute = 0;
    private int firstHour = 0;
    private String[] hours = new String[28];
    private String[] minutes = new String[64];
    TextView textView;
    private String[] dateToast;
    private String[] timeToast;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.dateBtn);
        time = findViewById(R.id.timeBtn);
        save = findViewById(R.id.saveBtn);

        daysList = findViewById(R.id.recyclerViewDays);
        monthsList = findViewById(R.id.recyclerViewMonths);
        yearsList = findViewById(R.id.recyclerViewYears);
        hoursList = findViewById(R.id.recyclerViewHours);
        minutesList = findViewById(R.id.recyclerViewMinutes);
        textView = findViewById(R.id.currentDate);
        dateToast = new String[4];
        timeToast = new String[5];
        dateToast[0] = "Выбранная дата : ";
        dateToast[1] = " 1 ";
        dateToast[2] = "января ";
        dateToast[3] = "2000 ";
        timeToast[0] = " Выбранное время : ";
        timeToast[1] = " Вам ";
        timeToast[2] = "необходимо ";
        timeToast[3] = " выбрать";
        timeToast[4] = " время";

        years[0] = " - ";
        years[1] = " - ";
        years[29] = " - ";
        years[28] = " - ";
        days[0] = " - ";
        days[1] = " - ";
        days[30] = " - ";
        days[31] = " - ";
        hours[0] = " - ";
        hours[1] = " - ";
        hours[26] = " - ";
        hours[27] = " - ";
        minutes[63] = " - ";
        minutes[62] = " - ";
        minutes[0] = " - ";
        minutes[1] = " - ";




        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManager4 = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManager5 = new LinearLayoutManager(this);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=false;
                firstYear = 2000;
                firstDay = 1;
                firstMinute = 0;
                firstHour = 0;

                minutesList.setVisibility(View.VISIBLE);
                hoursList.setVisibility(View.VISIBLE);

                yearsList.setVisibility(View.GONE);
                daysList.setVisibility(View.GONE);
                monthsList.setVisibility(View.GONE);

                for (int n = 2; n < hours.length-2; n++) {
                    hours[n] = String.valueOf(firstHour++);
                }
                for (int n = 2; n < minutes.length-2; n++) {
                    minutes[n] = String.valueOf(firstMinute++);
                }


                hoursList.setLayoutManager(layoutManager4);
                minutesList.setLayoutManager(layoutManager5);


                hoursList.setHasFixedSize(true);
                minutesList.setHasFixedSize(true);

                dateAdapter1 = new DateAdapter(hours);
                dateAdapter2 = new DateAdapter(minutes);

                hoursList.setAdapter(dateAdapter1);
                minutesList.setAdapter(dateAdapter2);

                final SnapHelper snapHelper1 = new LinearSnapHelper();
                final SnapHelper snapHelper2 = new LinearSnapHelper();

                snapHelper1.attachToRecyclerView(hoursList);
                hoursList.setOnFlingListener(null);
                snapHelper2.attachToRecyclerView(minutesList);
                minutesList.setOnFlingListener(null);

                hoursList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper1.findSnapView(layoutManager4);
                            int pos = layoutManager4.getPosition(centerView);
                            Log.e("Snapped Item Position:", "" + pos);
                            timeToast[1] = String.valueOf(hours[pos]);
                            timeToast[2] = " часов ";

                        }
                    }
                });
                minutesList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper2.findSnapView(layoutManager5);
                            int pos = layoutManager5.getPosition(centerView);
                            Log.e("Snapped Item Position:", "" + pos);
                            timeToast[3] = String.valueOf(minutes[pos]);
                            timeToast[4] = " минут ";
                        }
                    }
                });

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=true;
                firstYear = 2000;
                firstDay = 1;
                yearsList.setVisibility(View.VISIBLE);
                monthsList.setVisibility(View.VISIBLE);
                daysList.setVisibility(View.VISIBLE);

                minutesList.setVisibility(View.GONE);
                hoursList.setVisibility(View.GONE);
                for (int n = 2; n < years.length-2; n++) {
                    years[n] = String.valueOf(firstYear++);
                }
                for (int n = 2; n < days.length-2; n++) {
                    days[n] = String.valueOf(firstDay++);
                }

                months = new ArrayList<>();


                months.add(" - ");
                months.add(" - ");
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
                months.add(" - ");
                months.add(" - ");




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
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper1.findSnapView(layoutManager1);
                            int pos = layoutManager1.getPosition(centerView);
                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[1] = String.valueOf(days[pos]) + " ";
                        }
                    }
                });
                monthsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper2.findSnapView(layoutManager2);
                            int pos = layoutManager2.getPosition(centerView);
                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[2] = String.valueOf(months.get(pos)) + " ";

                        }
                    }
                });
                yearsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            View centerView = snapHelper3.findSnapView(layoutManager3);
                            int pos = layoutManager3.getPosition(centerView);
                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[3] = String.valueOf(years[pos]) + " ";

                        }
                    }
                });
            }
        });

        for (int n = 2; n < years.length-2; n++) {
            years[n] = String.valueOf(firstYear++);
        }
        for (int n = 2; n < days.length-2; n++) {
            days[n] = String.valueOf(firstDay++);
        }


        months = new ArrayList<>();
        months.add(" - ");
        months.add(" - ");
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
        months.add(" - ");
        months.add(" - ");



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
                if (newState == RecyclerView.SCROLL_STATE_IDLE + 2) {
                    View centerView = snapHelper1.findSnapView(layoutManager1);
                    int pos = layoutManager1.getPosition(centerView);
                    Log.e("Snapped Item Position:", "" + pos);
                    dateToast[1] = String.valueOf(days[pos]) + " ";
                }
            }
        });
        monthsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE + 2) {
                    View centerView = snapHelper2.findSnapView(layoutManager2);
                    int pos = layoutManager2.getPosition(centerView);
                    Log.e("Snapped Item Position:", "" + pos);
                    dateToast[2] = String.valueOf(months.get(pos)) + " ";
                }
            }
        });
        yearsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE + 2) {
                    View centerView = snapHelper3.findSnapView(layoutManager3);
                    int pos = layoutManager3.getPosition(centerView);
                    Log.e("Snapped Item Position:", "" + pos);
                    dateToast[3] = String.valueOf(years[pos]) + " ";
                }
                ///////////////

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = yearsList.getChildCount();
                totalItemCount = layoutManager3.getItemCount();
                firstVisibleItem = layoutManager3.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    Log.i("Yaeye!", "end called");

                    // Do something

                    loading = true;
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        StringBuilder stringBuilder1 = new StringBuilder();
                                        StringBuilder stringBuilder2 = new StringBuilder();
                                        for (String s : dateToast) {
                                            stringBuilder1.append(s);
                                        }
                                        for (String s : timeToast) {
                                            stringBuilder2.append(s);
                                        }
                                        if(flag) Toast.makeText(getBaseContext(), String.valueOf(stringBuilder1), Toast.LENGTH_SHORT).show();
                                        else Toast.makeText(getBaseContext(), String.valueOf(stringBuilder2), Toast.LENGTH_SHORT).show();
                                    }
                                }
        );

    }
}
