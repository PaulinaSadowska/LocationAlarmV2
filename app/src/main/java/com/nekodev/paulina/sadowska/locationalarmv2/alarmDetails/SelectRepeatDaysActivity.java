package com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Paulina Sadowska on 15.05.2016.
 */
public class SelectRepeatDaysActivity extends AppCompatActivity {

    @Bind(R.id.repeat_days_every)
    CheckBox dayEvery;
    @Bind({R.id.repeat_days_monday, R.id.repeat_days_tuesday, R.id.repeat_days_wednesday, R.id.repeat_days_thursday,
            R.id.repeat_days_friday, R.id.repeat_days_saturday, R.id.repeat_days_sunday})
    List<CheckBox> dayCheckboxes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_repeat_days);
        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        dayEvery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for (CheckBox checkbox : dayCheckboxes) {
                        checkbox.setChecked(true);
                    }
                }
                else{
                    for (CheckBox checkbox : dayCheckboxes) {
                        checkbox.setChecked(false);
                    }
                }
            }
        });
    }

    @OnClick(R.id.repeat_days_save_button)
    public void onSaveClick(){
        boolean[] days = new boolean[7];
        for (int i = 0; i < dayCheckboxes.size(); i++) {
            days[i] = dayCheckboxes.get(i).isChecked();
        }
        setResult(Activity.RESULT_OK, getIntent().putExtra(Keys.REPEAT_DAYS_KEY, days));
        finish();
    }
}
