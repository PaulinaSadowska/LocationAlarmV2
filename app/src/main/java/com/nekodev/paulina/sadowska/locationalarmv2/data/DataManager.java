package com.nekodev.paulina.sadowska.locationalarmv2.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
