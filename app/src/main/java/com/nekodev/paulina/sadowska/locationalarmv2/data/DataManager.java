package com.nekodev.paulina.sadowska.locationalarmv2.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Paulina Sadowska on 23.05.2016.
 */
public class DataManager {

    private FileManager fileReader;
    private Map<Integer, AlarmDataItem> mAlarmDataItems;


    public DataManager(String filesDir, String fileName) {
        fileReader = new FileManager(filesDir, fileName);
        initAlarmCollection();
    }

    private void initAlarmCollection(){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<AlarmDataItem>>(){}.getType();
        String response = fileReader.readFromFile();
        mAlarmDataItems = gson.fromJson(response, collectionType);
        if(mAlarmDataItems == null){
            mAlarmDataItems = new HashMap<>();
        }
    }

    public int getAvailableId() {
        int availableId = 0;
        Random r = new Random();

        while(mAlarmDataItems.containsKey(availableId)) {
            availableId = r.nextInt(10000);
        }
        return availableId;
    }

    public void resetMockAlarmData(){
        mAlarmDataItems.clear();

        AlarmDataItem item = new AlarmDataItem(getAvailableId());
        item.setIsActive(true);
        item.setRadiusInMeters(3000);
        item.setAlarmType(AlarmTypes.NOTIFICATION);
        item.setLocation("London");
         item.setRepeatDays(new boolean[]{true, false, false, true, false, true, false});
        item.setCoordinates(new LatLng(1.33, 14.32));

        mAlarmDataItems.put(item.getAlarmId(), item);

        AlarmDataItem item2 = new AlarmDataItem(getAvailableId());
        item2.setIsActive(false);
        item2.setRadiusInMeters(500);
        item2.setAlarmType(AlarmTypes.SOUND);
        item2.setLocation("Paris");
        item2.setRepeatDays(new boolean[]{true, true, true, true, true, true, true});
        item2.setCoordinates(new LatLng(0.22, 22.3));

        mAlarmDataItems.put(item2.getAlarmId(), item2);

        save();
    }

    public Collection<AlarmDataItem> getAlarmData(){
        return mAlarmDataItems.values();
    }

    public int addAlarm(LatLng coordinates, String address, int radius){
        AlarmDataItem alarm = new AlarmDataItem(getAvailableId());
        alarm.setCoordinates(coordinates);
        alarm.setLocation(address);
        alarm.setRadiusInMeters(radius);
        mAlarmDataItems.put(alarm.getAlarmId(), alarm);
        save();
        return alarm.getAlarmId();
    }

    public AlarmDataItem get(int alarmId){
        return mAlarmDataItems.get(alarmId);
    }

    public void editAlarmDetails(int alarmId, AlarmTypes alarmType, String alarmTone, boolean[] repeatDays)
    {
        AlarmDataItem item = mAlarmDataItems.get(alarmId);
        item.setAlarmType(alarmType);
        item.setAlarmTone(alarmTone);
        item.setRepeatDays(repeatDays);
        save();
    }

    public void editAlarmActive(int alarmId, boolean isActive)
    {
        AlarmDataItem item = mAlarmDataItems.get(alarmId);
        item.setIsActive(isActive);
        save();
    }

    public void remove(int alarmId){
        mAlarmDataItems.remove(alarmId);
        save();
    }

    private void save(){
        Gson gson = new Gson();
        String json = gson.toJson(mAlarmDataItems);
        fileReader.saveToFile(json);
    }

    public int numberOfAlarms() {
        return mAlarmDataItems.size();
    }
}
