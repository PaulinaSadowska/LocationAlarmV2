package com.nekodev.paulina.sadowska.locationalarmv2;

/**
 * Created by Paulina Sadowska on 28.03.16.
 */
public class Utilities {

    public static int kilometersToMeters(double km){
        return (int) (km * 1000.0);
    }

    public static int kilometersToMeters(String km){
        return kilometersToMeters(Double.parseDouble(km));
    }

    public static int kilometersToMeters(CharSequence km){
        return kilometersToMeters(km.toString());
    }


}
