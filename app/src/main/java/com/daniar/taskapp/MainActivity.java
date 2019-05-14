package com.daniar.taskapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.daniar.taskapp.room.Task;
import com.daniar.taskapp.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isShown = sharedPreferences.getBoolean("isShown", false);
        if (true) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    public void onClick(View view) {
        startActivity(new Intent(this, TaskActivity.class));
        overridePendingTransition(R.anim.right_in_alpha, R.anim.left_out_alpha );
    }

}