package com.nekodev.paulina.sadowska.locationalarmv2.data;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Paulina Sadowska on 23.05.2016.
 */
public class FileManager {

    private String fileName;
    private String filesDir; //this.getFilesDir()

    public FileManager(String filesDir, String fileName){
        this.filesDir = filesDir;
        this.fileName = fileName;
    }

   public String readFromFile() {
        BufferedReader br;
        String response;

        try {

            StringBuffer output = new StringBuffer();
            String fpath = filesDir + "/" + fileName;

            br = new BufferedReader(new java.io.FileReader(fpath));
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return response;
    }

    public void saveToFile(String json) {
        FileOutputStream outputStream;

        try {
            String fpath = filesDir+"/"+fileName;
            outputStream = new FileOutputStream(fpath);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
