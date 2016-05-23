package com.nekodev.paulina.sadowska.locationalarmv2.data;

/**
 * Created by Paulina Sadowska on 23.05.2016.
 */
public class AlarmDataManager {

    private FileReader fileReader;

    public AlarmDataManager(String filesDir, String fileName) {
        fileReader = new FileReader(filesDir, fileName);
    }

    /*DataObject obj = new DataObject();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        saveJsonToFile(json);
        String response = readJsonFromFile();
        DataObject obj2 = gson.fromJson(response, DataObject.class);
        */

}
