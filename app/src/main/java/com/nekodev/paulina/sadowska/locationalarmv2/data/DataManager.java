package com.nekodev.paulina.sadowska.locationalarmv2.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 23.05.2016.
 */
public class DataManager {

    private FileManager fileReader;
    private ArrayList<AlarmDataItem> mAlarmDataItems = new ArrayList<>();


    public DataManager(String filesDir, String fileName) {
        fileReader = new FileManager(filesDir, fileName);

        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<AlarmDataItem>>(){}.getType();
        String response = fileReader.readFromFile();
        mAlarmDataItems = gson.fromJson(response, collectionType);
    }

    public void resetMockAlarmData(){
        AlarmDataItem item = new AlarmDataItem(1);
        item.setIsActive(true);
        item.setRadiusInMeters(3000);
        item.setAlarmType(AlarmTypes.NOTIFICATION);
        item.setLocation("London");
         item.setRepeatDays(new boolean[]{true, false, false, true, false, true, false});
        item.setCoordinates(new LatLng(1.33, 14.32));

        AlarmDataItem item2 = new AlarmDataItem(1);
        item2.setIsActive(false);
        item2.setRadiusInMeters(500);
        item2.setAlarmType(AlarmTypes.SOUND);
        item2.setLocation("Paris");
        item2.setRepeatDays(new boolean[]{true, true, true, true, true, true, true});
        item2.setCoordinates(new LatLng(0.22, 22.3));

        mAlarmDataItems.clear();
        mAlarmDataItems.add(item);
        mAlarmDataItems.add(item2);

        Gson gson = new Gson();
        String json = gson.toJson(mAlarmDataItems);
        fileReader.saveToFile(json);
    }

    public ArrayList<AlarmDataItem> getAlarmData(){
        return mAlarmDataItems;
    }

    public void addAlarm(AlarmDataItem alarmDataItem){
        mAlarmDataItems.add(alarmDataItem);
        Gson gson = new Gson();
        String json = gson.toJson(mAlarmDataItems);
        fileReader.saveToFile(json);
    }
}
