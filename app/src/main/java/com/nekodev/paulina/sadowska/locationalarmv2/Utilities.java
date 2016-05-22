package com.nekodev.paulina.sadowska.locationalarmv2;

import android.content.res.Resources;

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

    public static String getActiveDaysString(Resources res, boolean[] activeDays) {
        String result = "";
        String[] daysShortNames = res.getStringArray(R.array.days_short_names);
        int activeCount = 0;
        for (int i = 0; i < activeDays.length; i++) {
            if (activeDays[i]) {
                result += daysShortNames[i] + ", ";
                activeCount++;
            }
        }
        if (activeCount == activeDays.length) {
            return res.getString(R.string.day_every);
        } else {
            if (result.length() > 0) {
                result = result.substring(0, result.length() - 2);
            } else {
                result = res.getString(R.string.day_none);
            }
            return result;
        }
    }


}
