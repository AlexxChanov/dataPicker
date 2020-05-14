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
    private int prevCenterPos;
    boolean flag = true;
    private RecyclerView daysList, monthsList, yearsList, hoursList, minutesList;
    private DateAdapter dateAdapter1, dateAdapter2, dateAdapter3;
    Button date, time, save;
    TextView textView;
    private int firstYear = 2000;
    private int firstDay = 1;
    private int centerItem = 2;
    private int firstItem = 0;
    private int lastItem = 4;
    private int firstMinute = 0;
    private int firstHour = 0;
    private int positionOnScroll = 0;
    private String[] years = new String[30];
    private String[] hours = new String[28];
    private String[] minutes = new String[64];
    private String[] timeToast;
    private String[] dateToast;
    private ArrayList<String> months = new ArrayList<>(Arrays.asList(" - ", " - ", "янв.", "фев", "март", "апрель", "май", "июнь", "июль", "авг.", "сент.", "окт.", "нояб.", "дек.", "-", "-"));
    private ArrayList<String> days = new ArrayList<>(Arrays.asList(" - ", " - ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 ", " 10 ", " 11 ", " 12 ", " 13 ", " 14 ", " 15 ", " 16 ", " 17 ", " 18 ", " 19 ", " 20 ", " 21 ", " 22 ", " 23 ", " 24 ", " 25 ", " 26 ", " 27 ", " 28 ", " 29 ", " 30 ", " 31 ", " - ", " - "));
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
                                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {

                                v.vibrate(200);
                            }
                            timeToast[1] = String.valueOf(hours[pos]);
                            timeToast[2] = " часов ";
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        View childVieww = hoursList.getChildAt(centerItem);
                        View childViewPrev = hoursList.getChildAt(centerItem - 1);
                        View childViewNext = hoursList.getChildAt(centerItem + 1);
                        View childViewFirst =hoursList.getChildAt(firstItem);
                        View childViewLast = hoursList.getChildAt(lastItem);
                        TextView childTextVieww = childVieww.findViewById(R.id.currentDate);
                        TextView childTextViewwPrev = childViewPrev.findViewById(R.id.currentDate);
                        TextView childTextViewwNext = childViewNext.findViewById(R.id.currentDate);
                        TextView childTextViewwFirst = childViewFirst.findViewById(R.id.currentDate);
                        TextView childTextViewwLast = childViewLast.findViewById(R.id.currentDate);
                        childTextVieww.setTextColor(Color.RED);
                        childTextViewwPrev.setTextColor(Color.BLACK);
                        childTextViewwNext.setTextColor(Color.BLACK);
                        childTextViewwFirst.setTextColor(Color.GRAY);
                        childTextViewwLast.setTextColor(Color.GRAY);

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
                                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {

                                v.vibrate(200);
                            }
                            timeToast[3] = String.valueOf(minutes[pos]);
                            timeToast[4] = " минут ";
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        View childVieww = minutesList.getChildAt(centerItem);
                        View childViewPrev = minutesList.getChildAt(centerItem - 1);
                        View childViewNext = minutesList.getChildAt(centerItem + 1);
                        View childViewFirst =minutesList.getChildAt(firstItem);
                        View childViewLast = minutesList.getChildAt(lastItem);
                        TextView childTextVieww = childVieww.findViewById(R.id.currentDate);
                        TextView childTextViewwPrev = childViewPrev.findViewById(R.id.currentDate);
                        TextView childTextViewwNext = childViewNext.findViewById(R.id.currentDate);
                        TextView childTextViewwFirst = childViewFirst.findViewById(R.id.currentDate);
                        TextView childTextViewwLast = childViewLast.findViewById(R.id.currentDate);
                        childTextVieww.setTextColor(Color.RED);
                        childTextViewwPrev.setTextColor(Color.BLACK);
                        childTextViewwNext.setTextColor(Color.BLACK);
                        childTextViewwFirst.setTextColor(Color.GRAY);
                        childTextViewwLast.setTextColor(Color.GRAY);
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
                                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                v.vibrate(200);
                            }
                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[1] = days.get(pos) + " ";
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        View childVieww = daysList.getChildAt(centerItem);
                        View childViewPrev = daysList.getChildAt(centerItem - 1);
                        View childViewNext = daysList.getChildAt(centerItem + 1);
                        View childViewFirst =daysList.getChildAt(firstItem);
                        View childViewLast = daysList.getChildAt(lastItem);
                        TextView childTextVieww = childVieww.findViewById(R.id.currentDate);
                        TextView childTextViewwPrev = childViewPrev.findViewById(R.id.currentDate);
                        TextView childTextViewwNext = childViewNext.findViewById(R.id.currentDate);
                        TextView childTextViewwFirst = childViewFirst.findViewById(R.id.currentDate);
                        TextView childTextViewwLast = childViewLast.findViewById(R.id.currentDate);
                        childTextVieww.setTextColor(Color.RED);
                        childTextViewwPrev.setTextColor(Color.BLACK);
                        childTextViewwNext.setTextColor(Color.BLACK);
                        childTextViewwFirst.setTextColor(Color.GRAY);
                        childTextViewwLast.setTextColor(Color.GRAY);
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
                                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {

                                v.vibrate(200);
                            }
                            scrollSong.start();
                            if (pos == 5 || pos == 7 || pos == 10 || pos == 12) {

                                if (days.size() == 35) {
                                    days.remove(32);
                                    dateAdapter1.notifyDataSetChanged();
                                } else if (days.size() == 32) {
                                    days.remove(31);
                                    days.remove(30);
                                    days.add(30, " 29 ");
                                    days.add(31, " 30 ");
                                    days.add(32, " - ");
                                    days.add(33, " - ");
                                    dateAdapter1.notifyDataSetChanged();
                                } else Log.d("chto to ne tak", String.valueOf(days.size()));

                            } else if (pos == 3) {
                                if (days.size() == 35) {
                                    days.remove(32);
                                    days.remove(31);
                                    days.remove(30);
                                    dateAdapter1.notifyDataSetChanged();
                                } else if (days.size() == 34) {
                                    days.remove(31);
                                    days.remove(30);
                                    dateAdapter1.notifyDataSetChanged();
                                } else Log.d("chto to ne tak", String.valueOf(days.size()));
                                dateAdapter1.notifyDataSetChanged();
                            } else {
                                if (days.size() == 32) {
                                    days.remove(31);
                                    days.remove(30);
                                    days.add(30, " 29 ");
                                    days.add(31, " 30 ");
                                    days.add(32, " 31 ");
                                    days.add(33, " - ");
                                    days.add(34, " - ");
                                    dateAdapter1.notifyDataSetChanged();
                                }

                                if (days.size() == 34) {
                                    days.remove(33);
                                    days.remove(32);
                                    days.add(32, " 31 ");
                                    days.add(33, " - ");
                                    days.add(34, " - ");
                                    dateAdapter1.notifyDataSetChanged();
                                } else Log.d("chto to ne tak", String.valueOf(days.size()));


                            }

                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[2] = months.get(pos) + " ";
                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        View childVieww = monthsList.getChildAt(centerItem);
                        View childViewPrev = monthsList.getChildAt(centerItem - 1);
                        View childViewNext = monthsList.getChildAt(centerItem + 1);
                        View childViewFirst =monthsList.getChildAt(firstItem);
                        View childViewLast = monthsList.getChildAt(lastItem);
                        TextView childTextVieww = childVieww.findViewById(R.id.currentDate);
                        TextView childTextViewwPrev = childViewPrev.findViewById(R.id.currentDate);
                        TextView childTextViewwNext = childViewNext.findViewById(R.id.currentDate);
                        TextView childTextViewwFirst = childViewFirst.findViewById(R.id.currentDate);
                        TextView childTextViewwLast = childViewLast.findViewById(R.id.currentDate);
                        childTextVieww.setTextColor(Color.RED);
                        childTextViewwPrev.setTextColor(Color.BLACK);
                        childTextViewwNext.setTextColor(Color.BLACK);
                        childTextViewwFirst.setTextColor(Color.GRAY);
                        childTextViewwLast.setTextColor(Color.GRAY);

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
                                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                v.vibrate(200);
                            }
                            Log.e("Snapped Item Position:", "" + pos);
                            dateToast[3] = years[pos] + " ";
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        View childVieww = yearsList.getChildAt(centerItem);
                        View childViewPrev = yearsList.getChildAt(centerItem - 1);
                        View childViewNext = yearsList.getChildAt(centerItem + 1);
                        View childViewFirst =yearsList.getChildAt(firstItem);
                        View childViewLast = yearsList.getChildAt(lastItem);
                        TextView childTextVieww = childVieww.findViewById(R.id.currentDate);
                        TextView childTextViewwPrev = childViewPrev.findViewById(R.id.currentDate);
                        TextView childTextViewwNext = childViewNext.findViewById(R.id.currentDate);
                        TextView childTextViewwFirst = childViewFirst.findViewById(R.id.currentDate);
                        TextView childTextViewwLast = childViewLast.findViewById(R.id.currentDate);
                        childTextVieww.setTextColor(Color.RED);
                        childTextViewwPrev.setTextColor(Color.BLACK);
                        childTextViewwNext.setTextColor(Color.BLACK);
                        childTextViewwFirst.setTextColor(Color.GRAY);
                        childTextViewwLast.setTextColor(Color.GRAY);

                    }
                });
            }
        });

        for (int n = 2; n < years.length - 2; n++) {
            years[n] = String.valueOf(firstYear++);
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE + 2) {
                    View centerView = snapHelper1.findSnapView(layoutManager1);
                    int pos = layoutManager1.getPosition(centerView);
                    scrollSong.start();

                    //вибрация
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        v.vibrate(200);
                    }
                    //лог
                    Log.e("Snapped Item Position:", "" + pos);
                    //сохранение для вывода в тост
                    dateToast[1] = days.get(pos) + " ";


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View childVieww = daysList.getChildAt(centerItem);
                View childViewPrev = daysList.getChildAt(centerItem - 1);
                View childViewNext = daysList.getChildAt(centerItem + 1);
                View childViewFirst =daysList.getChildAt(firstItem);
                View childViewLast = daysList.getChildAt(lastItem);
                TextView childTextVieww = childVieww.findViewById(R.id.currentDate);
                TextView childTextViewwPrev = childViewPrev.findViewById(R.id.currentDate);
                TextView childTextViewwNext = childViewNext.findViewById(R.id.currentDate);
                TextView childTextViewwFirst = childViewFirst.findViewById(R.id.currentDate);
                TextView childTextViewwLast = childViewLast.findViewById(R.id.currentDate);
                childTextVieww.setTextColor(Color.RED);
                childTextViewwPrev.setTextColor(Color.BLACK);
                childTextViewwNext.setTextColor(Color.BLACK);
                childTextViewwFirst.setTextColor(Color.GRAY);
                childTextViewwLast.setTextColor(Color.GRAY);

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
                        v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        v.vibrate(200);
                    }
                    scrollSong.start();
                    if (pos == 5 || pos == 7 || pos == 10 || pos == 12) {

                        if (days.size() == 35) {
                            days.remove(32);
                            dateAdapter1.notifyDataSetChanged();
                        } else if (days.size() == 32) {
                            days.remove(31);
                            days.remove(30);
                            days.add(30, " 29 ");
                            days.add(31, " 30 ");
                            days.add(32, " - ");
                            days.add(33, " - ");
                            dateAdapter1.notifyDataSetChanged();
                        } else Log.d("chto to ne tak", String.valueOf(days.size()));

                    } else if (pos == 3) {
                        if (days.size() == 35) {
                            days.remove(32);
                            days.remove(31);
                            days.remove(30);
                            dateAdapter1.notifyDataSetChanged();
                        } else if (days.size() == 34) {
                            days.remove(31);
                            days.remove(30);
                            dateAdapter1.notifyDataSetChanged();
                        } else Log.d("chto to ne tak", String.valueOf(days.size()));
                        dateAdapter1.notifyDataSetChanged();
                    } else {
                        if (days.size() == 32) {
                            days.remove(31);
                            days.remove(30);
                            days.add(30, " 29 ");
                            days.add(31, " 30 ");
                            days.add(32, " 31 ");
                            days.add(33, " - ");
                            days.add(34, " - ");
                            dateAdapter1.notifyDataSetChanged();
                        }

                        if (days.size() == 34) {
                            days.remove(33);
                            days.remove(32);
                            days.add(32, " 31 ");
                            days.add(33, " - ");
                            days.add(34, " - ");
                            dateAdapter1.notifyDataSetChanged();
                        } else Log.d("chto to ne tak", String.valueOf(days.size()));


                    }

                    Log.e("Snapped Item Position:", "" + pos);
                    dateToast[2] = months.get(pos) + " ";
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View childVieww = monthsList.getChildAt(centerItem);
                View childViewPrev = monthsList.getChildAt(centerItem - 1);
                View childViewNext = monthsList.getChildAt(centerItem + 1);
                View childViewFirst =monthsList.getChildAt(firstItem);
                View childViewLast = monthsList.getChildAt(lastItem);
                TextView childTextVieww = childVieww.findViewById(R.id.currentDate);
                TextView childTextViewwPrev = childViewPrev.findViewById(R.id.currentDate);
                TextView childTextViewwNext = childViewNext.findViewById(R.id.currentDate);
                TextView childTextViewwFirst = childViewFirst.findViewById(R.id.currentDate);
                TextView childTextViewwLast = childViewLast.findViewById(R.id.currentDate);
                childTextVieww.setTextColor(Color.RED);
                childTextViewwPrev.setTextColor(Color.BLACK);
                childTextViewwNext.setTextColor(Color.BLACK);
                childTextViewwFirst.setTextColor(Color.GRAY);
                childTextViewwLast.setTextColor(Color.GRAY);


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
                        v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        v.vibrate(200);
                    }
                    dateToast[3] = years[pos] + " ";
                    positionOnScroll = pos;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View childVieww = yearsList.getChildAt(centerItem);
                View childViewPrev = yearsList.getChildAt(centerItem - 1);
                View childViewNext = yearsList.getChildAt(centerItem + 1);
                View childViewFirst = yearsList.getChildAt(firstItem);
                View childViewLast = yearsList.getChildAt(lastItem);
                TextView childTextVieww = childVieww.findViewById(R.id.currentDate);
                TextView childTextViewwPrev = childViewPrev.findViewById(R.id.currentDate);
                TextView childTextViewwNext = childViewNext.findViewById(R.id.currentDate);
                TextView childTextViewwFirst = childViewFirst.findViewById(R.id.currentDate);
                TextView childTextViewwLast = childViewLast.findViewById(R.id.currentDate);
                childTextVieww.setTextColor(Color.RED);
                childTextViewwPrev.setTextColor(Color.BLACK);
                childTextViewwNext.setTextColor(Color.BLACK);
                childTextViewwFirst.setTextColor(Color.GRAY);
                childTextViewwLast.setTextColor(Color.GRAY);

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
