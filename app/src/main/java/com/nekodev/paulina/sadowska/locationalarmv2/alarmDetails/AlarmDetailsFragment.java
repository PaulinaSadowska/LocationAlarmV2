package com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;

/**
 * Created by Paulina Sadowska on 15.05.2016.
 */
public class AlarmDetailsFragment extends Fragment {

    private AlarmDetailsItemSpinner alarmTypeFragment = new AlarmDetailsItemSpinner();
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

        Resources res = getResources();

        alarmTypeFragment.setArguments(getAlarmTypeArg(res.getString(R.string.alarm_type), res.getStringArray(R.array.alarm_types_list)));
        alarmTypeFragment.setOnSpinnerSelectionChangedListener(new AlarmDetailsItemSpinner.SpinnerSelectionChangedListener() {
            @Override
            public void onSpinnerSelectionChanged(int position) {
                if(position==0) { //TODO - alarm type indexes hardcoded - uglyyy!
                    alarmType = AlarmTypes.SOUND;
                }
                else{
                    alarmType = AlarmTypes.NOTIFICATION;
                }
            }
        });
        alarmSoundFragment.setArguments(getAlarmArg(res.getString(R.string.alarm_tone), "Morning Flower"));
        alarmSoundFragment.setOnItemClickedListener(new AlarmDetailsItem.ItemClickedListener() {
            @Override
            public void onItemClicked() {
                if(alarmType==AlarmTypes.SOUND)
                    startSoundPickerActivity(RingtoneManager.TYPE_ALARM);
                else if(alarmType == AlarmTypes.NOTIFICATION)
                    startSoundPickerActivity(RingtoneManager.TYPE_NOTIFICATION);
            }
        });
        alarmRepeatingFragment.setArguments(getAlarmArg(res.getString(R.string.repeat_weekly_title) + ":", res.getString(R.string.day_none)));
        alarmRepeatingFragment.setOnItemClickedListener(new AlarmDetailsItem.ItemClickedListener() {
            @Override
            public void onItemClicked() {
                Intent intent = new Intent(getActivity(), SelectRepeatDaysActivity.class);
                getActivity().startActivityForResult(intent, Keys.ActivityResultKeys.SELECT_DAYS_REQUEST_CODE);
            }
        });
    }

    private void startSoundPickerActivity(int soundTypes) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getResources().getString(R.string.select_ringtone));
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, soundTypes);
        Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        RingtoneManager.setActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_RINGTONE, uri);
        getActivity().startActivityForResult(intent, Keys.ActivityResultKeys.SELECT_SOUND_REQUEST_CODE);
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
            case Keys.ActivityResultKeys.SELECT_SOUND_REQUEST_CODE:
                if(resultCode== Activity.RESULT_OK) {
                    Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), uri);
                        alarmSoundFragment.setOptionText(ringtone.getTitle(getActivity()));
                    }
                }
                break;
            case Keys.ActivityResultKeys.SELECT_DAYS_REQUEST_CODE:
                if(resultCode== Activity.RESULT_OK) {
                    boolean[] result = intent.getBooleanArrayExtra(Keys.REPEAT_DAYS_KEY);
                    if(result!=null && result.length>0) {
                        setActiveDays(result);
                    }
                }
                break;

        }
    }

    public Bundle getAlarmTypeArg(String title, String[] options) {
        Bundle args = new Bundle();
        args.putString(Keys.AlarmDetailsItemKeys.ITEM_TITLE_KEY, title);
        args.putSerializable(Keys.AlarmDetailsItemKeys.ITEM_OPTION_KEY, options);
        return args;
    }

    public void setActiveDays(boolean[] activeDays) {
        String result = "";
        Resources res = getResources();
        String[] daysShortNames = res.getStringArray(R.array.days_short_names);
        int activeCount = 0;
        for (int i = 0; i < activeDays.length; i++) {
            if(activeDays[i]){
                result += daysShortNames[i]+", ";
                activeCount++;
            }
        }
        if(activeCount == activeDays.length){
            alarmRepeatingFragment.setOptionText(res.getString(R.string.day_every));
        }
        else {
            if (result.length() > 0) {
                result = result.substring(0, result.length() - 2);
            }
            else{
                result = res.getString(R.string.day_none);
            }
            alarmRepeatingFragment.setOptionText(result);
        }
    }
}
