package com.nekodev.paulina.sadowska.locationalarmv2;

/**
 * Created by Paulina Sadowska on 26.05.2016.
 */
public class Constants {

    public static final String FILE_NAME = "alarms";
    public final static int MIN_DALAY_MS = 60 * 1000; //1min

    public static final int ALARM_TYPE_SOUND_CODE = 0;
    public static final int ALARM_TYPE_NOTIFICATION_CODE = 1;

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 24 * 356 * 100; //never stops :>
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
}
