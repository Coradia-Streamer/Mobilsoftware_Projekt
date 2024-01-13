package com.coradia.mobilsoftware_projekt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.color.MaterialColors;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
/*
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
*/
        Button mainButton = findViewById(R.id.main_button);
        Button mapButton = findViewById(R.id.map_button);
        Button detailsButton = findViewById(R.id.details_button);

        detailsButton.setBackgroundColor(MaterialColors.getColor(this, androidx.appcompat.R.attr.colorPrimary, Color.WHITE));

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