package com.nekodev.paulina.sadowska.locationalarmv2.chooseLocation;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Paulina Sadowska on 22.05.2016.
 */
public class ChosenLocationData {

    private LatLng coordinates;
    private String address;
    private String previewImageName;
    private int radius;

    public ChosenLocationData(LatLng coordinates, CharSequence address, int radius , String previewImageName) {
        this(coordinates, address.toString(), radius, previewImageName);
    }

    public ChosenLocationData(LatLng coordinates, String address, int radius, String previewImageName) {
        this.coordinates = coordinates;
        this.address = address;
        this.radius = radius;
        this.previewImageName = previewImageName;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress(CharSequence address) {
        this.address = address.toString();
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String getAddress() {
        return address;
    }

    public String getPreviewImageName() {
        return previewImageName;
    }

    public int getRadius() {
        return radius;
    }
}
