package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

import com.google.android.gms.maps.model.LatLng;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;

/**
 * Created by Paulina Sadowska on 12.03.16.
 */
public class AlarmDataItem {

    private Boolean isActive;
    private String imageName;
    private LatLng coordinates;
    private String address;
    private int radiusInMeters;
    private AlarmTypes alarmType;
    private String alarmTone;
    private boolean[] repeatDays;

    public AlarmDataItem() {
        repeatDays = new boolean[7];
    }

    public void setIsActive(Boolean isActive) {
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


    public Boolean getIsActive() {
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
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
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

