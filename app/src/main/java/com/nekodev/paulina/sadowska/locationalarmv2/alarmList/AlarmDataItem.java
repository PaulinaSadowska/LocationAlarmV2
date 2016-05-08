package com.nekodev.paulina.sadowska.locationalarmv2.alarmList;

/**
 * Created by Paulina Sadowska on 12.03.16.
 */
public class AlarmDataItem {

    private String label;
    private Boolean isActive;
    private String location;
    private int radiusInMeters;
    private Boolean isCyclic;
    private String activeDays;
    private String ringtone;
    private Boolean notificationOnly;

    public AlarmDataItem() {
        isActive = true;
        location = "";
        radiusInMeters = 1000;
        isCyclic = false;
        activeDays = "";
        ringtone = "";
        notificationOnly = false;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRadiusInMeters(int radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public void setIsCyclic(Boolean isCyclic) {
        this.isCyclic = isCyclic;
    }

    public void setActiveDays(String activeDays) {
        this.activeDays = activeDays;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public void setNotificationOnly(Boolean notificationOnly) {
        this.notificationOnly = notificationOnly;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public String getLocation() {
        return location;
    }

    public int getRadiusInMeters() {
        return radiusInMeters;
    }

    public double getRadiusInKilometers() {
        return radiusInMeters / 1000.0;
    }

    public Boolean getIsCyclic() {
        return isCyclic;
    }

    public String getActiveDays() {
        return activeDays;
    }

    public String getRingtone() {
        return ringtone;
    }

    public Boolean getNotificationOnly() {
        return notificationOnly;
    }
}

