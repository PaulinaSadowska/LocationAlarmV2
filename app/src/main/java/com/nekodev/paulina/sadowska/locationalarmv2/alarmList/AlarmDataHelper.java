package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

import android.content.SharedPreferences;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Paulina Sadowska on 22.05.2016.
 */
//WRONG ATTEMPT - probably delete it
public class AlarmDataHelper {

    private SharedPreferences preferences;
    private HashMap<Integer, AlarmDataItem> alarms;


    private AlarmDataHelper(SharedPreferences preferences){
        this.preferences = preferences;
        //SharedPreferences.Editor editor = preferences.edit();
    }

    public void addAlarm(AlarmDataItem alarm){

    }

    public void deleteAlarm(AlarmDataItem alarm){

    }

    public ArrayList<AlarmDataItem> getSavedAlarmsList(){
        ArrayList<AlarmDataItem> alarms = new ArrayList<>();
        int alarmCount = preferences.getInt(Keys.AlarmData.ALARM_COUNT, 0);
        if(alarmCount>0){
            for(int i=0; i<alarmCount; i++) {
                alarms.add(getSavedAlarm(i));
            }
        }
        return alarms;
    }

    private AlarmDataItem getSavedAlarm(int alarmNumber){
        AlarmDataItem dataItem = new AlarmDataItem();
        dataItem.setIsActive(preferences.getBoolean(Keys.AlarmData.IS_ACTIVE, false));
        dataItem.setImageName(preferences.getString(Keys.AlarmData.IMAGE_NAME, ""));
        Float lat = preferences.getFloat(Keys.AlarmData.LATITUDE, 0);
        Float lng = preferences.getFloat(Keys.AlarmData.LONGITUDE, 0);
        dataItem.setCoordinates(new LatLng(lat, lng));
        dataItem.setLocation(preferences.getString(Keys.AlarmData.ADDRESS, ""));
        dataItem.setRadiusInMeters(preferences.getInt(Keys.AlarmData.RADIUS, 0));
        int alarmType = preferences.getInt(Keys.AlarmData.ALARM_TYPE, 0);
        if(alarmType == 0){ //TODO - FIX alarm type indexes hardcoded
            dataItem.setAlarmType(AlarmTypes.SOUND);
        }
        else{
            dataItem.setAlarmType(AlarmTypes.NOTIFICATION);
        }
        dataItem.setAlarmTone(Uri.parse(preferences.getString(Keys.AlarmData.ALARM_TONE, "")));
        boolean repeatDays[] = new boolean[7];
        for(int i=0; i<7; i++){
            repeatDays[i] = preferences.getBoolean(Keys.AlarmData.REPEAT_DAYS+i, false);
        }
        dataItem.setRepeatDays(repeatDays);
        return dataItem;
    }
}
