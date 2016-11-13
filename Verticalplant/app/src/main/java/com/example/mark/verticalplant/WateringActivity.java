package com.example.mark.verticalplant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.CircleProgress;

public class WateringActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private CircleProgress circleProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watering);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        //get Value from preference
        sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        int level = sharedPreferences.getInt("waterLevel",0);

        circleProgress = (CircleProgress) findViewById(R.id.circle_progress);
        circleProgress.setProgress(level);
    }

    public void addWater(View view){
        Integer currentLevel = circleProgress.getProgress();
        if (currentLevel < 100){
            currentLevel+=10;
        }else{
            currentLevel = 100;
            Toast.makeText(this, "Enough today", Toast.LENGTH_SHORT).show();
        }
        circleProgress.setProgress(currentLevel);

        editor = sharedPreferences.edit();
        editor.putInt("waterLevel", currentLevel);
        editor.putString("waterStatus", "Just watered");
        editor.commit();
    }

}
