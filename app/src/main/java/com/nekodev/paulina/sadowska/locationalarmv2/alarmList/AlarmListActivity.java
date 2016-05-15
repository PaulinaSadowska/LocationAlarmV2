package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmDetailsActivity;
import com.nekodev.paulina.sadowska.locationalarmv2.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Paulina Sadowska on 08.05.2016.
 */
public class AlarmListActivity extends AppCompatActivity {

    @Bind(R.id.alarm_list_add_alarm_button)
    FloatingActionButton addAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.alarm_list_add_alarm_button)
    public void addAlarm(View view) {
        Intent intent = new Intent(this, AlarmDetailsActivity.class);
        startActivity(intent);
    }
}
