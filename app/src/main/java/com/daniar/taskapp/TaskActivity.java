package com.daniar.taskapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.daniar.taskapp.room.Task;

import java.util.Calendar;
import java.util.Date;

public class TaskActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editDesc;
    long time;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);

        task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
        }
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_view);
//        editTitle.startAnimation(animation);
    }

    public void onClickDate(View view) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog picker = new DatePickerDialog(this, 0, null,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        picker.setButton(DialogInterface.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int d = picker.getDatePicker().getDayOfMonth();
                int m = picker.getDatePicker().getMonth();
                int y = picker.getDatePicker().getYear();
                Calendar c = Calendar.getInstance();
                c.set(y, m, d);
                time = c.getTimeInMillis();
            }
        });
        picker.show();
    }

    public void onClickSave(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        if (time == 0) time = System.currentTimeMillis();

        if (task != null) {
            task.setTitle(title);
            task.setDesc(desc);
            task.setTime(time);
            App.getInstance().getDatabase().taskDao().update(task);
        } else {
            Task task = new Task();
            task.setTitle(title);
            task.setDesc(desc);
            task.setTime(time);
            App.getInstance().getDatabase().taskDao().insert(task);
        }
        finish();
    }
}