package jp.ac.meijou.android.superalarmclock;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.superalarmclock.databinding.ActivitySetInfoBinding;

public class SetInfoActivity extends AppCompatActivity {

    private ActivitySetInfoBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = PrefDataStore.getInstance(this);

        // Cancelボタンが押されたとき
        binding.buttonCancel.setOnClickListener(view -> {
            finish();
        });

        // Saveボタンが押されたとき
        binding.buttonSave.setOnClickListener(view -> {
            var mon = binding.spinnerMon.getSelectedItem().toString();
            var tue = binding.spinnerTue.getSelectedItem().toString();
            var wed = binding.spinnerWed.getSelectedItem().toString();
            var thu = binding.spinnerThu.getSelectedItem().toString();
            var fri = binding.spinnerFri.getSelectedItem().toString();
            var sat = binding.spinnerSat.getSelectedItem().toString();
            var commute = binding.editText.getSelectedItem().toString();

            prefDataStore.setString("mon", mon);
            prefDataStore.setString("tue", tue);
            prefDataStore.setString("wed", wed);
            prefDataStore.setString("thu", thu);
            prefDataStore.setString("fri", fri);
            prefDataStore.setString("sat", sat);
            prefDataStore.setString("commute", commute);

            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        prefDataStore.getString("mon")
                        .ifPresent(mon -> setSelection(binding.spinnerMon, mon));
        prefDataStore.getString("tue")
                        .ifPresent(tue -> setSelection(binding.spinnerTue, tue));
        prefDataStore.getString("wed")
                        .ifPresent(wed -> setSelection(binding.spinnerWed, wed));
        prefDataStore.getString("thu")
                        .ifPresent(thu -> setSelection(binding.spinnerThu, thu));
        prefDataStore.getString("fri")
                        .ifPresent(fri -> setSelection(binding.spinnerFri, fri));
        prefDataStore.getString("sat")
                        .ifPresent(sat -> setSelection(binding.spinnerSat, sat));
        prefDataStore.getString("commute")
                        .ifPresent(time -> setSelection(binding.editText, time));
        Log.d(TAG, "onResume: Change values of commute");
    }

    public static void setSelection(@NonNull Spinner spinner, String item) {
        SpinnerAdapter adapter = spinner.getAdapter();
        int idx = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(item)) {
                idx = i;
                break;
            }
        }
        spinner.setSelection(idx);
    }
}