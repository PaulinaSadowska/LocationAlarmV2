package com.nekodev.paulina.sadowska.locationalarmv2;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Paulina Sadowska on 27.05.2016.
 */
public class AlarmActivity extends AppCompatActivity {

    @Bind(R.id.alarm_activity_address)
    TextView address;

    private Ringtone ringtone;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        ButterKnife.bind(this);
        address.setText(getIntent().getStringExtra(Keys.AlarmDetailsKeys.ALARM_LOCATION_ADDRESS));
        startAlarmSound(getIntent().getStringExtra(Keys.AlarmDetailsKeys.ALARM_TONE_ADDRESS));
    }

    public void onAttachedToWindow() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    private void startAlarmSound(String alarmSoundAddress) {
        try {
            Uri sound;
            if(alarmSoundAddress == null){
                sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            }else{
                sound = Uri.parse(alarmSoundAddress);
            }
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), sound);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.alarm_activity_cancelAlarm)
    public void cancelAlarm(View view) {
        ringtone.stop();
        finish();
    }

    @Override
    public void onBackPressed() {
        ringtone.stop();
        finish();
    }
}
