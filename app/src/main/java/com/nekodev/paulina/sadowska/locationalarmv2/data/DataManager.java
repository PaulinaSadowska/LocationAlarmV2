package com.nekodev.paulina.sadowska.locationalarmv2.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;

/**
 * Created by Paulina Sadowska on 23.05.2016.
 */
public class DataManager {

    private FileManager fileReader;

    public DataManager(String filesDir, String fileName) {
        fileReader = new FileManager(filesDir, fileName);
        AlarmDataItem item = new AlarmDataItem(1);
        item.setIsActive(true);
        item.setRadiusInMeters(3000);
        item.setAlarmType(AlarmTypes.NOTIFICATION);
        item.setLocation("Piotrowo 2");
        // item.setRepeatDays(new boolean[]{true, false, false, true, false, true, false});
        item.setCoordinates(new LatLng(0.22, 22.3));
        item.setAlarmTone("some tone");
        Gson gson = new Gson();
        String json = gson.toJson(item);
        fileReader.saveToFile(json);
        String response = fileReader.readFromFile();
        AlarmDataItem obj2 = gson.fromJson(response, AlarmDataItem.class);

    }

    /*DataObject obj = new DataObject();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        saveJsonToFile(json);
        String response = readJsonFromFile();
        DataObject obj2 = gson.fromJson(response, DataObject.class);
        */

}
