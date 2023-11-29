package com.coradia.mobilsoftware_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mapButton = findViewById(R.id.mapButton);

        mapButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(MainActivity.this,MapActivity.class);
            startActivity(intent2);
        });
    }
}