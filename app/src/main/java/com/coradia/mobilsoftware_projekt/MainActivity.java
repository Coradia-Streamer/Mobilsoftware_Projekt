package com.coradia.mobilsoftware_projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.coradia.mobilsoftware_projekt.methods.PopUp;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private final Context activityContext = MainActivity.this;
    private Intent starterIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedTheme = sharedPreferences.getInt("SelectedTheme", 0);
        setDynamicTheme(selectedTheme);

        setContentView(R.layout.activity_main);

        boolean togglePopSettings = sharedPreferences.getBoolean("togglePopSettings", false);
        if (togglePopSettings) {
            PopUp popUp = new PopUp();
            popUp.openPopUpWindow(findViewById(R.id.popUp_view), activityContext, sharedPreferences, "MainActivity");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("togglePopSettings", false);

        editor.putBoolean("toggleDetails", false);
        editor.apply();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Button mainButton = findViewById(R.id.main_button);
        Button mapButton = findViewById(R.id.map_button);
        Button detailsButton = findViewById(R.id.details_button);

        mainButton.setBackgroundColor(Color.parseColor("#EBD11C"));

        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,MapActivity.class);
            starterIntent = intent;
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        detailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
            starterIntent = intent;
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        boolean toggleReCreate = sharedPreferences.getBoolean("mainCallReCreate", false);
        if (toggleReCreate && starterIntent != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("mainCallReCreate", false);
            editor.apply();
            Intent intentRe = new Intent(MainActivity.this,MainActivity.class);
            finish();
            startActivity(intentRe);
        } else if (starterIntent == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("mainCallReCreate", false);
            editor.apply();
        }
        super.onResume();
    }

    private void setDynamicTheme(int selectedTheme) {
        switch (selectedTheme) {
            case 0:
                MainActivity.this.setTheme(R.style.Theme_Mobilsoftware_Projekt);
                break;
            case 1:
                MainActivity.this.setTheme(R.style.LightTheme);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            PopUp popUp = new PopUp();
            popUp.openPopUpWindow(findViewById(R.id.popUp_view), activityContext, sharedPreferences, "MainActivity");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}