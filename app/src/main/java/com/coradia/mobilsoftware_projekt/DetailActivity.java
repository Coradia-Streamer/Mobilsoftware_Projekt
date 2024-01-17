package com.coradia.mobilsoftware_projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.coradia.mobilsoftware_projekt.methods.PopUp;

import java.util.Objects;
import java.util.StringTokenizer;

public class DetailActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private final Context activityContext = DetailActivity.this;
    private Intent starterIntent;
    private boolean toggleDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedTheme = sharedPreferences.getInt("SelectedTheme", 0);
        setDynamicTheme(selectedTheme);

        setContentView(R.layout.activity_detail);

        boolean togglePopSettings = sharedPreferences.getBoolean("togglePopSettings", false);
        if (togglePopSettings) {
            PopUp popUp = new PopUp();
            popUp.openPopUpWindow(findViewById(R.id.popUp_view), activityContext, sharedPreferences, "DetailActivity");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("togglePopSettings", false);
        editor.apply();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Button mainButton = findViewById(R.id.main_button);
        Button mapButton = findViewById(R.id.map_button);
        Button detailsButton = findViewById(R.id.details_button);

        detailsButton.setBackgroundColor(Color.parseColor("#EBD11C"));

        mainButton.setOnClickListener(view -> {
            Intent intent = new Intent(DetailActivity.this,MainActivity.class);
            starterIntent = intent;
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(DetailActivity.this,MapActivity.class);
            starterIntent = intent;
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
    }

    Handler h = new Handler();
    Runnable r;
    int delay = 1000;
    @Override
    protected void onResume() {
        boolean toggleReCreate = sharedPreferences.getBoolean("detailCallReCreate", false);
        if (toggleReCreate && starterIntent != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("detailCallReCreate", false);
            editor.apply();
            Intent intentRe = new Intent(DetailActivity.this,DetailActivity.class);
            finish();
            startActivity(intentRe);
        } else if (starterIntent == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("detailCallReCreate", false);
            editor.apply();
        }
        super.onResume();

        TextView grade_waiting = findViewById(R.id.grade_waiting);
        ScrollView score = findViewById(R.id.scroll);
        TextView grade_final_num = findViewById(R.id.grade_final_num);
        TextView grade_pt_num = findViewById(R.id.grade_pt_num);
        TextView grade_pt1_num = findViewById(R.id.grade_pt1_num);
        TextView grade_pt2_num = findViewById(R.id.grade_pt2_num);
        TextView grade_pt3_num = findViewById(R.id.grade_pt3_num);
        TextView grade_pt4_num = findViewById(R.id.grade_pt4_num);
        TextView grade_pt1_val = findViewById(R.id.grade_pt1_val);
        TextView grade_pt2_val = findViewById(R.id.grade_pt2_val);
        TextView grade_pt3_val = findViewById(R.id.grade_pt3_val);
        TextView grade_pt4_val = findViewById(R.id.grade_pt4_val);
        TextView grade_bike_num = findViewById(R.id.grade_bike_num);
        TextView grade_bike1_num = findViewById(R.id.grade_bike1_num);
        TextView grade_bike2_num = findViewById(R.id.grade_bike2_num);
        TextView grade_bike3_num = findViewById(R.id.grade_bike3_num);
        TextView grade_bike1_val = findViewById(R.id.grade_bike1_val);
        TextView grade_bike2_val = findViewById(R.id.grade_bike2_val);
        TextView grade_bike3_val = findViewById(R.id.grade_bike3_val);

        h.postDelayed(r = () -> {
            toggleDetails = sharedPreferences.getBoolean("toggleDetails", false);
            if (toggleDetails) {
                String grade = sharedPreferences.getString("scoreDetails", "");
                String values = sharedPreferences.getString("valueList", "");
                if (!grade.equals("")) {
                    StringTokenizer v = new StringTokenizer(values, ",");
                    String[] ptValue = new String[4];
                    String[] bikeValue = new String[3];

                    StringTokenizer g = new StringTokenizer(grade, ";");

                    String finalGrade = g.nextToken();

                    String[] ptGrade = new String[5];
                    ptGrade[0] = g.nextToken();

                    StringTokenizer pt = new StringTokenizer(g.nextToken(), ",");
                    for (int i = 0; i < 4; i++) {
                        ptGrade[i+1] = pt.nextToken();
                        ptValue[i] = v.nextToken();
                    }
                    ptValue[1] = ptValue[1] + "m";

                    String[] bikeGrade = new String[4];
                    bikeGrade[0] = g.nextToken();

                    StringTokenizer bike = new StringTokenizer(g.nextToken(), ",");
                    for (int i = 0; i < 3; i++) {
                        bikeGrade[i+1] = bike.nextToken();
                        bikeValue[i] = v.nextToken();
                    }
                    bikeValue[2] = bikeValue[2] + "m";

                    Log.i("Grade", "Final Grade: " + finalGrade);
                    for (int i = 0; i < ptGrade.length; i++) {
                        Log.i("Grade", "PublicTransport Grade" + i + ": " + ptGrade[i]);
                    }
                    for (int i = 0; i < bikeGrade.length; i++) {
                        Log.i("Grade", "Bike Grade" + i + ": " + bikeGrade[i]);
                    }

                    grade_final_num.setText(finalGrade);
                    grade_pt_num.setText(ptGrade[0]);
                    grade_pt1_num.setText(ptGrade[1]);
                    grade_pt2_num.setText(ptGrade[2]);
                    grade_pt3_num.setText(ptGrade[3]);
                    grade_pt4_num.setText(ptGrade[4]);
                    grade_pt1_val.setText(ptValue[0]);
                    if (ptValue[1].equals("-1m")) {
                        grade_pt2_val.setText(R.string.no_value);
                    } else grade_pt2_val.setText(ptValue[1]);
                    if (ptValue[2].equals("-1")) {
                        grade_pt3_val.setText("0");
                    } else grade_pt3_val.setText(ptValue[2]);
                    grade_pt4_val.setText(ptValue[3]);
                    grade_bike_num.setText(bikeGrade[0]);
                    grade_bike1_num.setText(bikeGrade[1]);
                    grade_bike2_num.setText(bikeGrade[2]);
                    grade_bike3_num.setText(bikeGrade[3]);
                    grade_bike1_val.setText(bikeValue[0]);
                    grade_bike2_val.setText(bikeValue[1]);
                    if (bikeValue[2].equals("-1m")) {
                        grade_bike3_val.setText(R.string.no_value);
                    } else grade_bike3_val.setText(bikeValue[2]);
                    grade_waiting.setVisibility(View.INVISIBLE);
                    score.setVisibility(View.VISIBLE);
                }
            }
        }, delay);
    }

    private void setDynamicTheme(int selectedTheme) {
        switch (selectedTheme) {
            case 0:
                DetailActivity.this.setTheme(R.style.Theme_Mobilsoftware_Projekt);
                break;
            case 1:
                DetailActivity.this.setTheme(R.style.LightTheme);
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
            popUp.openPopUpWindow(findViewById(R.id.popUp_view), activityContext, sharedPreferences, "DetailActivity");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}