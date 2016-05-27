package com.nekodev.paulina.sadowska.locationalarmv2.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Paulina Sadowska on 23.05.2016.
 */
public class DataManager {

    private FileManager fileReader;
    private Map<Integer, AlarmDataItem> mAlarmDataItems = new HashMap<>();
    private static DataManager manager;

    private OnDataChangedListener listener;
    public void setOnDataCHangedListener(OnDataChangedListener listener){
        this.listener = listener;
    }


    public static DataManager getInstance(String filesDir, String fileName){
        if(manager == null){
            manager = new DataManager(filesDir, fileName);
        }
        return manager;
    }

    private DataManager(String filesDir, String fileName) {
        fileReader = new FileManager(filesDir, fileName);
        initAlarmCollection();
    }

    private void initAlarmCollection(){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<HashMap<Integer, AlarmDataItem>>(){}.getType();
        String response = fileReader.readFromFile();
        mAlarmDataItems = gson.fromJson(response, collectionType);
        if(mAlarmDataItems == null){
            mAlarmDataItems = new HashMap<>();
        }
    }

    private int getAvailableId() {
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
        item.setAddress("London");
         item.setRepeatDays(new boolean[]{true, false, false, true, false, true, false});
        item.setCoordinates(new LatLng(1.33, 14.32));

        mAlarmDataItems.put(item.getAlarmId(), item);

        save();
    }

    private void notifyDataSetChanged(){
        if(listener!=null){
            listener.alarmDataChanged();
        }
    }

    public int addAlarm(LatLng coordinates, String address, int radius){
        AlarmDataItem alarm = new AlarmDataItem(getAvailableId());
        alarm.setCoordinates(coordinates);
        alarm.setAddress(address);
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

    public void editAlarmIsActive(int alarmId, boolean isActive)
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
        notifyDataSetChanged();
    }

    public int numberOfAlarms() {
        return mAlarmDataItems.size();
    }

    public String getImageId(int alarmId) {
        return mAlarmDataItems.get(alarmId).getImageName();
    }

    public Set<Integer> getKeys() {
        return mAlarmDataItems.keySet();
    }

    public void editAlarmLocation(int alarmId, LatLng localization, String address, int radius) {
        AlarmDataItem item = mAlarmDataItems.get(alarmId);
        item.setCoordinates(localization);
        item.setAddress(address);
        item.setRadiusInMeters(radius);
        save();
    }
}
