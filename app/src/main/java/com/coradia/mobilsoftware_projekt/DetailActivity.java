package com.coradia.mobilsoftware_projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Button mainButton = findViewById(R.id.main_button);
        Button mapButton = findViewById(R.id.map_button);
        Button detailsButton = findViewById(R.id.details_button);

        detailsButton.setBackgroundColor(getColor(R.color.Alarmred));

        mainButton.setOnClickListener(view -> {
            Intent intent = new Intent(DetailActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(DetailActivity.this,MapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
    }
}