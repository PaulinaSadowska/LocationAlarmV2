package com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.nekodev.paulina.sadowska.locationalarmv2.R;

/**
 * Created by Paulina Sadowska on 15.05.2016.
 */
public class SelectRepeatDaysActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_repeat_days);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }
}
