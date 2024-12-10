package jp.ac.meijou.android.superalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
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
        prefDataStore = PrefDataStore.getInstance(this
        );

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
            prefDataStore.setString("mon", mon);
            prefDataStore.setString("tue", tue);
            prefDataStore.setString("wed", wed);
            prefDataStore.setString("thu", thu);
            prefDataStore.setString("fri", fri);
            prefDataStore.setString("sat", sat);

            var intent = new Intent(this, TopActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}