package com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;

/**
 * Created by Paulina Sadowska on 15.05.2016.
 */
public class AlarmDetailsFragment extends Fragment {


    private static final int SELECT_SOUND_REQUEST_CODE = 999;
    private AlarmDetailsItem alarmTypeFragment = new AlarmDetailsItem();
    private AlarmDetailsItem alarmSoundFragment = new AlarmDetailsItem();
    private AlarmDetailsItem alarmRepeatingFragment = new AlarmDetailsItem();
    private AlarmTypes alarmType = AlarmTypes.SOUND;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.alarm_details_alarm_type, alarmTypeFragment);
        transaction.replace(R.id.alarm_details_alarm_sound, alarmSoundFragment);
        transaction.replace(R.id.alarm_details_alarm_repeating, alarmRepeatingFragment);
        transaction.commit();

        alarmTypeFragment.setArguments(getAlarmArg("Alarm type:", "Sound"));
        alarmTypeFragment.setOnItemClickedListener(new AlarmDetailsItem.ItemClickedListener() {
            @Override
            public void onItemClicked() {
                Toast.makeText(getActivity(), "start alarm type choose dialog", Toast.LENGTH_SHORT).show();
            }
        });
        alarmSoundFragment.setArguments(getAlarmArg("Alarm tone:", "Morning Flower"));
        alarmSoundFragment.setOnItemClickedListener(new AlarmDetailsItem.ItemClickedListener() {
            @Override
            public void onItemClicked() {
                if(alarmType==AlarmTypes.SOUND)
                    startSoundPickerActivity(RingtoneManager.TYPE_ALARM);
                else if(alarmType == AlarmTypes.NOTIFICATION)
                    startSoundPickerActivity(RingtoneManager.TYPE_NOTIFICATION);
            }
        });
        alarmRepeatingFragment.setArguments(getAlarmArg("Repeat weekly:", "Mon, Wed, Sat, Sun"));
        alarmRepeatingFragment.setOnItemClickedListener(new AlarmDetailsItem.ItemClickedListener() {
            @Override
            public void onItemClicked() {
                Toast.makeText(getActivity(), "start choose days dialog", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startSoundPickerActivity(int soundTypes) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select ringtone for notifications:");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, soundTypes);
        Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        RingtoneManager.setActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_RINGTONE, uri);
        getActivity().startActivityForResult(intent,SELECT_SOUND_REQUEST_CODE);
    }

    private Bundle getAlarmArg(String title, String option){
        Bundle args = new Bundle();
        args.putString(Keys.AlarmDetailsItemKeys.ITEM_TITLE_KEY, title);
        args.putString(Keys.AlarmDetailsItemKeys.ITEM_OPTION_KEY, option);
        return args;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
       // super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode){
            case SELECT_SOUND_REQUEST_CODE:
                if(resultCode== Activity.RESULT_OK) {
                    Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), uri);
                        alarmSoundFragment.setOptionText(ringtone.getTitle(getActivity()));
                    }
                }
                break;
        }
    }
}
