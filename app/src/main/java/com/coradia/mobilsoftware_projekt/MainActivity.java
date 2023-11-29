package com.coradia.mobilsoftware_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView middleText = findViewById(R.id.middleText);
        Button firstButton = findViewById(R.id.firstButton);
        Button secondButton = findViewById(R.id.secondButton);
        Button mapButton = findViewById(R.id.mapButton);

        final String[] randText = {"LOL"};
        List<String> randList = new ArrayList<>();
        Collections.addAll(randList,"This","is","some","text","in","a","list");

        firstButton.setOnClickListener(view -> {
            int random = new Random().nextInt(randList.size());
            randText[0] = randList.get(random);
            middleText.setText(randText[0]);
            secondButton.setVisibility(View.VISIBLE);
        });
        secondButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtra(KEY, randText[0]);
            startActivity(intent);
        });

        mapButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(MainActivity.this,MapActivity.class);
            startActivity(intent2);
        });
    }
}