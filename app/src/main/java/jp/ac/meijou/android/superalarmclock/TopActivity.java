package jp.ac.meijou.android.superalarmclock;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.superalarmclock.databinding.ActivityTopBinding;

public class TopActivity extends AppCompatActivity {

    private ActivityTopBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
}