package com.nekodev.paulina.sadowska.locationalarmv2;

/**
 * Created by Paulina Sadowska on 15.05.2016.
 */
public final class Keys {

    public static final String ALARM_ID = "ALARM_ID";

    public final class ActivityResultKeys{
        public static final int SELECT_SOUND_REQUEST_CODE = 999;
        public static final int SELECT_DAYS_REQUEST_CODE = 444;
    }

    //used in alarm details fragment to pass data to nested fragments
    public final class AlarmDetailsItemKeys {
        public static final String ITEM_TITLE_KEY = "itemTitleKey";
        public static final String ITEM_OPTION_KEY = "itemOptionKey";
    }

    public final class AlarmDetailsKeys {
        public static final String ALARM_TYPE = "alarmTypeKey";
        public static final String ALARM_TONE = "alarmToneKey";
        public static final String REPEAT_DAYS = "repeatDays";
        public static final String ALARM_TONE_ADDRESS = "alarmToneAddress";
        public static final String ALARM_LOCATION_ADDRESS = "alarmLocationAddress";
    }

    public static final String REPEAT_DAYS_KEY = "repeatDaysKey";
}
