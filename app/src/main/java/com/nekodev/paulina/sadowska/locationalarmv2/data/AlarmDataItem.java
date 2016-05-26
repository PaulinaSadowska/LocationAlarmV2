package com.nekodev.paulina.sadowska.locationalarmv2.data;

import com.google.android.gms.maps.model.LatLng;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;

/**
 * Created by Paulina Sadowska on 12.03.16.
 */
public class AlarmDataItem {

    private int alarmId;
    private boolean isActive;
    private double latitude;
    private double longitude;
    private String address;
    private int radiusInMeters;
    private AlarmTypes alarmType;
    private String alarmTone;
    private boolean[] repeatDays;

    public AlarmDataItem(int alarmId) {

        repeatDays = new boolean[7];
        this.alarmId = alarmId;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setLocation(String location) {
        this.address = location;
    }

    public void setRadiusInMeters(int radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public void setAlarmTone(String alarmTone) {
        this.alarmTone = alarmTone;
    }


    public boolean getIsActive() {
        return isActive;
    }

    public String getAddress() {
        return address;
    }

    public int getRadiusInMeters() {
        return radiusInMeters;
    }

    public double getRadiusInKilometers() {
        return radiusInMeters / 1000.0;
    }

    public String getAlarmTone() {
        return alarmTone;
    }

    public String getImageName() {
        return alarmId+"";
    }

    public int getAlarmId() {
        return alarmId;
    }

    public LatLng getCoordinates() {
        return new LatLng(latitude, longitude);
    }

    public void setCoordinates(LatLng coordinates) {
        this.latitude = coordinates.latitude;
        this.longitude = coordinates.longitude;
    }

    public AlarmTypes getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmTypes alarmType) {
        this.alarmType = alarmType;
    }

    public boolean[] getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(boolean[] repeatDays) {
        this.repeatDays = repeatDays;
    }
}

