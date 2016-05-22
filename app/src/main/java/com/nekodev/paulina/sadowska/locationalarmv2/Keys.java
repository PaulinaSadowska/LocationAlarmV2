package com.nekodev.paulina.sadowska.locationalarmv2;

/**
 * Created by Paulina Sadowska on 15.05.2016.
 */
public final class Keys {

    public final class ActivityResultKeys{
        public static final int SELECT_SOUND_REQUEST_CODE = 999;
        public static final int SELECT_DAYS_REQUEST_CODE = 444;
    }

    public final class LocationData{
        public static final String LATITUDE = "chosenLocationDataLatitude";
        public static final String LONGITUDE = "chosenLocationDataLongitude";
        public static final String ADDRESS = "chosenLocationDataAddress";
        public static final String IMAGE_NAME = "chosenLocationDataImageName";
        public static final String RADIUS = "chosenLocationDataRadius";
    }

    public final class AlarmDetailsItemKeys {
        public static final String ITEM_TITLE_KEY = "itemTitleKey";
        public static final String ITEM_OPTION_KEY = "itemOptionKey";
    }

    public final class AlarmData{
        public static final String ALARM_COUNT = "alarmDataAlarmCount";
        public static final String IS_ACTIVE = "alarmDataItemIsActive";
        public static final String IMAGE_NAME = "alarmDataItemImageName";
        public static final String LATITUDE = "alarmDataItemLatitude";
        public static final String LONGITUDE = "alarmDataItemLongitude";
        public static final String ADDRESS = "alarmDataItemAddress";
        public static final String RADIUS = "alarmDataItemRadius";
        public static final String ALARM_TYPE = "alarmDataItemAlarmType";
        public static final String ALARM_TONE = "alarmDataItemAlarmTone";
        public static final String REPEAT_DAYS = "alarmDataItemRepeatDays";
    }

    public static final String REPEAT_DAYS_KEY = "repeatDaysKey";
}
