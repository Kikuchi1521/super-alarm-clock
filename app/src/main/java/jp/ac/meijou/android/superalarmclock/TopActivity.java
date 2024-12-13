package jp.ac.meijou.android.superalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

import jp.ac.meijou.android.superalarmclock.databinding.ActivityTopBinding;

public class TopActivity extends AppCompatActivity {

    private ActivityTopBinding binding;
    private PrefDataStore prefDataStore;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = PrefDataStore.getInstance(this);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        display_tomorrow_alarm(tomorrow);

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                int m = month + 1;
                String date_display = m + "/" + dayOfMonth;
                binding.textSelectedDay.setText(date_display);

                display_alarm(calendar);
            }// onSelectedDayChange
        });// calendarView.setOnDateChangeListener

        binding.buttonSetInfo.setOnClickListener(view -> {
            var intent = new Intent(this, SetInfoActivity.class);
            startActivity(intent);
        });

        binding.buttonSchedule.setOnClickListener(view -> {
            var intent = new Intent(this, ScheduleActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.textSelectedDay.setText("");
        binding.textAlarmTime.setText("日付を選択...");
    }

    private void timeConf(LocalTime t, DateTimeFormatter format) {
        binding.textAlarmTime.setText(t.format(format));
    }

    private void timeTomorrowConf(LocalTime t, DateTimeFormatter format) {
        binding.textClock.setText(t.format(format));
    }


    private void display_alarm(Calendar calendar) {
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

        AtomicReference<String> when_start = new AtomicReference<>("");
        switch (day_of_week) {
            case (Calendar.SUNDAY):
                prefDataStore.getString("sun").ifPresent(sun -> when_start.set(sun));
                break;
            case (Calendar.MONDAY):
                prefDataStore.getString("mon").ifPresent(mon -> when_start.set(mon));
                break;
            case (Calendar.TUESDAY):
                prefDataStore.getString("tue").ifPresent(tue -> when_start.set(tue));
                break;
            case (Calendar.WEDNESDAY):
                prefDataStore.getString("wed").ifPresent(wed -> when_start.set(wed));
                break;
            case (Calendar.THURSDAY):
                prefDataStore.getString("thu").ifPresent(thu -> when_start.set(thu));
                break;
            case (Calendar.FRIDAY):
                prefDataStore.getString("fri").ifPresent(fri -> when_start.set(fri));
                break;
            case (Calendar.SATURDAY):
                prefDataStore.getString("sat").ifPresent(sat -> when_start.set(sat));
                break;
            default:
                when_start.set("error");
                break;
        };

        prefDataStore.getString("commute")
                .ifPresent(str_commute -> {

                    LocalTime t;
                    if (str_commute.equals("未設定")) {
                        binding.textAlarmTime.setText("通学時間を設定してね");
                    } else {

                        int commute = Integer.parseInt(str_commute);
                        t = switch (when_start.get()) {
                            case "1限" -> LocalTime.of(9, 0).plusMinutes(commute);
                            case "2限" -> LocalTime.of(10, 40).plusMinutes(commute);
                            case "3限" -> LocalTime.of(13, 0).plusMinutes(commute);
                            case "4限" -> LocalTime.of(14, 40).plusMinutes(commute);
                            case "5限" -> LocalTime.of(16, 20).plusMinutes(commute);
                            case "6限" -> LocalTime.of(18, 0).plusMinutes(commute);
                            default -> LocalTime.of(0, 0);
                        };// switch

                        if (t.equals(LocalTime.of(0,0))) {
                            binding.textAlarmTime.setText("アラームなし");
                        } else {
                            timeConf(t, formatter);
                        }
                    }// if-else
                });
    }

    private void display_tomorrow_alarm(Calendar calendar) {
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

        AtomicReference<String> when_start = new AtomicReference<>("");
        switch (day_of_week) {
            case (Calendar.SUNDAY):
                prefDataStore.getString("sun").ifPresent(sun -> when_start.set(sun));
                break;
            case (Calendar.MONDAY):
                prefDataStore.getString("mon").ifPresent(mon -> when_start.set(mon));
                break;
            case (Calendar.TUESDAY):
                prefDataStore.getString("tue").ifPresent(tue -> when_start.set(tue));
                break;
            case (Calendar.WEDNESDAY):
                prefDataStore.getString("wed").ifPresent(wed -> when_start.set(wed));
                break;
            case (Calendar.THURSDAY):
                prefDataStore.getString("thu").ifPresent(thu -> when_start.set(thu));
                break;
            case (Calendar.FRIDAY):
                prefDataStore.getString("fri").ifPresent(fri -> when_start.set(fri));
                break;
            case (Calendar.SATURDAY):
                prefDataStore.getString("sat").ifPresent(sat -> when_start.set(sat));
                break;
            default:
                when_start.set("error");
                break;
        };

        prefDataStore.getString("commute")
                .ifPresent(str_commute -> {

                    LocalTime t;
                    if (str_commute.equals("未設定")) {
                        binding.textClock.setText("通学時間を設定してね");
                    } else {

                        int commute = Integer.parseInt(str_commute);
                        t = switch (when_start.get()) {
                            case "1限" -> LocalTime.of(9, 0).plusMinutes(commute);
                            case "2限" -> LocalTime.of(10, 40).plusMinutes(commute);
                            case "3限" -> LocalTime.of(13, 0).plusMinutes(commute);
                            case "4限" -> LocalTime.of(14, 40).plusMinutes(commute);
                            case "5限" -> LocalTime.of(16, 20).plusMinutes(commute);
                            case "6限" -> LocalTime.of(18, 0).plusMinutes(commute);
                            default -> LocalTime.of(0, 0);
                        };// switch

                        if (t.equals(LocalTime.of(0,0))) {
                            binding.textClock.setText("アラームなし");
                        } else {
                            timeTomorrowConf(t, formatter);
                        }
                    }// if-else
                });
    }
}