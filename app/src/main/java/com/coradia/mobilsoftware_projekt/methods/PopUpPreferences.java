package com.coradia.mobilsoftware_projekt.methods;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.coradia.mobilsoftware_projekt.MapActivity;
import com.coradia.mobilsoftware_projekt.R;

public class PopUpPreferences extends AppCompatActivity {

    public void openPopUpWindow(View view, Context context, SharedPreferences sharedPreferences) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_preferences, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popUp = new PopupWindow(popupView, width, height, focusable);

        Handler handler = new Handler();
        handler.postDelayed(() -> popUp.showAtLocation(view, Gravity.TOP, 0, 300), 100L);


        CheckBox check1 = findViewById(R.id.pop_pref_check1);
        CheckBox check2 = findViewById(R.id.pop_pref_check2);
        CheckBox check3 = findViewById(R.id.pop_pref_check3);
        CheckBox check4 = findViewById(R.id.pop_pref_check4);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Context context1;
        popupView.setOnTouchListener((view1, motionEvent) -> {
            view1.performClick();
            editor.putBoolean("prefCheck1", check1.isChecked());
            editor.putBoolean("prefCheck1", check2.isChecked());
            editor.putBoolean("prefCheck1", check3.isChecked());
            editor.putBoolean("prefCheck1", check4.isChecked());
            editor.putBoolean("togglePopPreferences", true);
            editor.apply();
            popUp.dismiss();
            return true;
        });
    }
}
