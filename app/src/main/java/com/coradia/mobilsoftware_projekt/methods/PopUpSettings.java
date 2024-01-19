package com.coradia.mobilsoftware_projekt.methods;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.coradia.mobilsoftware_projekt.R;

public class PopUpSettings extends AppCompatActivity {

    public void openPopUpWindow(View view, Context context, SharedPreferences sharedPreferences, String activityName) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_settings, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popUp = new PopupWindow(popupView, width, height, focusable);

        Handler handler = new Handler();
        handler.postDelayed(() -> popUp.showAtLocation(view, Gravity.TOP, 0, 300), 100L);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        SwitchCompat themeSwitch = popupView.findViewById(R.id.theme_switch);
        int selectedTheme = sharedPreferences.getInt("SelectedTheme", 0);
        if (selectedTheme == 1) themeSwitch.setChecked(true);
        themeSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                editor.putInt("SelectedTheme", 1);
            } else {
                editor.putInt("SelectedTheme", 0);
            }
            editor.putBoolean("togglePopSettings", true);
            switch (activityName) {
                case "MainActivity":
                    editor.putBoolean("mapCallReCreate", true);
                    editor.putBoolean("detailCallReCreate", true);
                    break;
                case "MapActivity":
                    editor.putBoolean("mainCallReCreate", true);
                    editor.putBoolean("detailCallReCreate", true);
                    break;
                case "DetailActivity":
                    editor.putBoolean("mainCallReCreate", true);
                    editor.putBoolean("mapCallReCreate", true);
                    break;
            }

            editor.apply();
            popUp.dismiss();
            context.startActivity(new Intent(context, context.getClass()));
            finish();
        });

        popupView.setOnTouchListener((view1, motionEvent) -> {
            view1.performClick();
            popUp.dismiss();
            return true;
        });
    }
}
