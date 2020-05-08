package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public int d;
    boolean flag = true;
    TextView currentDate;
    private RecyclerView daysList, monthsList, yearsList, hoursList, minutesList;
    private DateAdapter dateAdapter1, dateAdapter2, dateAdapter3;
    private ArrayList<String> months = new ArrayList<>(Arrays.asList(" - ", " - ", "янв.", "фев", "март", "апрель", "май", "июнь", "июль", "авг.", "сент.", "окт.", "нояб.", "дек.", "-", "-"));
    Button date, time, save;
    private String[] years = new String[30];
    private String[] days = new String[35];
    private int firstYear = 2000;
    private int firstDay = 1;
    private int firstMinute = 0;
    private int firstHour = 0;
    private String[] hours = new String[28];
    private String[] minutes = new String[64];
    TextView textView;
    private String[] dateToast;
    private String[] timeToast;
    private MediaPlayer scrollSong = new MediaPlayer();



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
        scrollSong = MediaPlayer.create(this, R.raw.scroll_sound);
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
        days[33] = " - ";
        days[34] = " - ";
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

                scrollSong.start();
                flag = false;
                firstYear = 2000;
                firstDay = 1;
                firstMinute = 0;
                firstHour = 0;

                minutesList.setVisibility(View.VISIBLE);
                hoursList.setVisibility(View.VISIBLE);

                yearsList.setVisibility(View.GONE);
                daysList.setVisibility(View.GONE);
                monthsList.setVisibility(View.GONE);

                for (int n = 2; n < hours.length - 2; n++) {
                    hours[n] = String.valueOf(firstHour++);
                }
                for (int n = 2; n < minutes.length - 2; n++) {
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
                            scrollSong.start();
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {

                                v.vibrate(500);
                            }
                            timeToast[1] = String.valueOf(hours[pos]);
                            timeToast[2] = " часов ";

                            int totalItemCount = layoutManager4.getChildCount();
                            for (int i = 0; i < totalItemCount; i++){
                                View childView = recyclerView.getChildAt(i);
                                TextView childTextView = childView.findViewById(R.id.currentDate);
                                String childTextViewText = (String) (childTextView.getText());

                                if (childTextViewText.equals(hours[pos]))
                                    childTextView.setTextColor(Color.RED);
                                else
                                    childTextView.setTextColor(Color.GRAY);

                            }
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
                            scrollSong.start();
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {

                                v.vibrate(500);
                            }
                            timeToast[3] = String.valueOf(minutes[pos]);
                            timeToast[4] = " минут ";

                            int totalItemCount = layoutManager5.getChildCount();
                            for (int i = 0; i < totalItemCount; i++){
                                View childView = recyclerView.getChildAt(i);
                                TextView childTextView = childView.findViewById(R.id.currentDate);
                                String childTextViewText = (String) (childTextView.getText());

                                if (childTextViewText.equals(minutes[pos]))
                                    childTextView.setTextColor(Color.RED);
                                else
                                    childTextView.setTextColor(Color.GRAY);

                            }
                        }
                    }
                });

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                firstYear = 2000;
                firstDay = 1;
                yearsList.setVisibility(View.VISIBLE);
                monthsList.setVisibility(View.VISIBLE);
                daysList.setVisibility(View.VISIBLE);

                minutesList.setVisibility(View.GONE);
                hoursList.setVisibility(View.GONE);
                for (int n = 2; n < years.length - 2; n++) {
                    years[n] = String.valueOf(firstYear++);
                }
                for (int n = 2; n < days.length - 2; n++) {
                    days[n] = String.valueOf(firstDay++);
                }

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
                            scrollSong.start();
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {

                                v.vibrate(500);
                            }
                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[1] = days[pos] + " ";

                            int totalItemCount = layoutManager1.getChildCount();
                            for (int i = 0; i < totalItemCount; i++){
                                View childView = recyclerView.getChildAt(i);
                                TextView childTextView = childView.findViewById(R.id.currentDate);
                                String childTextViewText = (String) (childTextView.getText());

                                if (childTextViewText.equals(days[pos]))
                                    childTextView.setTextColor(Color.RED);
                                else
                                    childTextView.setTextColor(Color.GRAY);

                            }

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
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {

                                v.vibrate(500);
                            }
                            scrollSong.start();
                            if (pos == 5 || pos == 7 || pos == 10 || pos == 12) {
                                days[32] = " - ";
                            } else if (pos == 3) {
                                days[32] = " - ";
                                days[31] = " - ";
                                days[30] = " - ";
                            } else {
                                days[32] = "31";
                                days[31] = "30";
                                days[30] = "29";
                            }

                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[2] = months.get(pos) + " ";


                            int totalItemCount = layoutManager2.getChildCount();
                            for (int i = 0; i < totalItemCount; i++){
                                View childView = recyclerView.getChildAt(i);
                                TextView childTextView = childView.findViewById(R.id.currentDate);
                                String childTextViewText = (String) (childTextView.getText());

                                if (childTextViewText.equals(months.get(pos)))
                                    childTextView.setTextColor(Color.RED);
                                else
                                    childTextView.setTextColor(Color.GRAY);

                            }
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
                            scrollSong.start();
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                v.vibrate(500);
                            }
                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[3] = years[pos] + " ";

                            int totalItemCount = layoutManager3.getChildCount();
                            for (int i = 0; i < totalItemCount; i++){
                                View childView = recyclerView.getChildAt(i);
                                TextView childTextView = childView.findViewById(R.id.currentDate);
                                String childTextViewText = (String) (childTextView.getText());

                                if (childTextViewText.equals(years[pos]))
                                    childTextView.setTextColor(Color.RED);
                                else
                                    childTextView.setTextColor(Color.GRAY);

                            }
                        }
                    }
                });
            }
        });

        for (int n = 2; n < years.length - 2; n++) {
            years[n] = String.valueOf(firstYear++);
        }
        for (int n = 2; n < days.length - 2; n++) {
            days[n] = String.valueOf(firstDay++);
        }

        daysList.setLayoutManager(layoutManager1);
        monthsList.setLayoutManager(layoutManager2);
        yearsList.setLayoutManager(layoutManager3);

        daysList.setHasFixedSize(true);//заранее знаем размер списка и эффективность повысится
        monthsList.setHasFixedSize(true);
        yearsList.setHasFixedSize(true);

        dateAdapter1 = new DateAdapter(days);
        dateAdapter2 = new DateAdapter(months);
        dateAdapter3 = new DateAdapter(years);

//        daysList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView,
//                                   int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//
//
//            }
//        });


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
                    scrollSong.start();

                    //вибрация
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        v.vibrate(500);
                    }
                    //лог
                    Log.e("Snapped Item Position:", "" + pos);
                    //сохранение для вывода в тост
                    dateToast[1] = days[pos] + " ";

                    int totalItemCount = layoutManager1.getChildCount();
                    for (int i = 0; i < totalItemCount; i++){
                        View childView = recyclerView.getChildAt(i);
                        TextView childTextView = childView.findViewById(R.id.currentDate);
                        String childTextViewText = (String) (childTextView.getText());

                        if (childTextViewText.equals(days[pos]))
                            childTextView.setTextColor(Color.RED);
                        else
                            childTextView.setTextColor(Color.GRAY);

                    }

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
                    scrollSong.start();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        v.vibrate(500);
                    }
                    if (pos == 5 || pos == 7 || pos == 10 || pos == 12) {
                        days[32] = " - ";
                    } else if (pos == 3) {
                        days[32] = " - ";
                        days[31] = " - ";
                        days[30] = " - ";
                    } else {
                        days[32] = "31";
                        days[31] = "30";
                        days[30] = "29";
                    }
                    Log.e("Snapped Item Position:", "" + pos);
                    dateToast[2] = months.get(pos) + " ";



                    int totalItemCount = layoutManager2.getChildCount();
                    for (int i = 0; i < totalItemCount; i++){
                        View childView = recyclerView.getChildAt(i);
                        TextView childTextView = childView.findViewById(R.id.currentDate);
                        String childTextViewText = (String) (childTextView.getText());

                        if (childTextViewText.equals(months.get(pos)))
                            childTextView.setTextColor(Color.RED);
                        else
                            childTextView.setTextColor(Color.GRAY);

                    }
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

                    scrollSong.start();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        v.vibrate(500);
                    }
                    dateToast[3] = years[pos] + " ";


                    int totalItemCount = layoutManager3.getChildCount();
                    for (int i = 0; i < totalItemCount; i++){
                        View childView = recyclerView.getChildAt(i);
                        TextView childTextView = childView.findViewById(R.id.currentDate);
                        String childTextViewText = (String) (childTextView.getText());

                        if (childTextViewText.equals(years[pos]))
                            childTextView.setTextColor(Color.RED);
                        else
                            childTextView.setTextColor(Color.GRAY);

                    }

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
                                        if (flag)
                                            Toast.makeText(getBaseContext(), String.valueOf(stringBuilder1), Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getBaseContext(), String.valueOf(stringBuilder2), Toast.LENGTH_SHORT).show();
                                    }
                                }
        );

    }
}
