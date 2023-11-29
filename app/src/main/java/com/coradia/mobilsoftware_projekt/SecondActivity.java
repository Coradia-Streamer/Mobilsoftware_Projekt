package com.coradia.mobilsoftware_projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.coradia.mobilsoftware_projekt.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView middleText2 = findViewById(R.id.middleText2);
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> super.onBackPressed());

        String message = getIntent().getExtras().getString(MainActivity.KEY);
        middleText2.setText(message);
    }
}