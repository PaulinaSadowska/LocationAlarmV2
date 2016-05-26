package com.nekodev.paulina.sadowska.locationalarmv2;

import com.google.android.gms.maps.model.LatLng;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;
import com.nekodev.paulina.sadowska.locationalarmv2.data.AlarmDataItem;
import com.nekodev.paulina.sadowska.locationalarmv2.data.DataManager;
import com.nekodev.paulina.sadowska.locationalarmv2.data.FileManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by Paulina Sadowska on 26.05.2016.
 */
public class DataManagerTest {

    private DataManager manager;

    private LatLng coordinates = new LatLng(22.01, 12.2);
    private String address = "Somewhere";
    private int radius = 1234;

    private String alarmTone = "some tone";
    private AlarmTypes alarmType = AlarmTypes.SOUND;
    private boolean[] repeatDays = new boolean[]{true, false, true, false, true, false, true};

    @Before
    public void setUp() throws Exception {
        manager = new DataManager("/Users/palka", "dummyName");
        Field field = DataManager.class.getDeclaredField("fileReader");
        field.setAccessible(true);
        FileManager mockFileReader = Mockito.mock(FileManager.class);
        when(mockFileReader.readFromFile()).thenReturn("");
        field.set(manager, mockFileReader);
    }

    @Test
    public void fileManagerMockTest() throws Exception {
        Field field = DataManager.class.getDeclaredField("fileReader");
        field.setAccessible(true);
        Object value = field.get(manager);
        FileManager man= (FileManager)value;
        assertEquals(man.readFromFile(), "");
    }

    @Test
    public void addAlarmTest(){
        int alarmId = manager.addAlarm(coordinates, address, radius);
        AlarmDataItem alarm = manager.get(alarmId);
        assertNotNull(alarm);
        assertEquals(alarmId, alarm.getAlarmId());
        assertEquals(coordinates, alarm.getCoordinates());
        assertEquals(address, alarm.getAddress());
        assertEquals(radius, alarm.getRadiusInMeters());
        assertNull(alarm.getAlarmTone());
        assertNull(alarm.getAlarmType());
        assertNotNull(alarm.getRepeatDays());
    }

    @Test
    public void editAlarmTest(){
        int alarmId = manager.addAlarm(coordinates, address, radius);
        manager.editAlarmDetails(alarmId, alarmType, alarmTone, repeatDays);
        AlarmDataItem alarm = manager.get(alarmId);

        assertNotNull(alarm);
        assertEquals(alarmTone, alarm.getAlarmTone());
        assertEquals(alarmType, alarm.getAlarmType());
        assertEquals(repeatDays, alarm.getRepeatDays());
    }

    @Test
    public void editAlarmDataOutsideManager_Test(){
        int alarmId = manager.addAlarm(coordinates, address, radius);
        manager.editAlarmDetails(alarmId, alarmType, alarmTone, repeatDays);
        AlarmDataItem alarm1 = manager.get(alarmId);
        String otherLocation = "other location";
        alarm1.setLocation(otherLocation);
        AlarmDataItem alarm = manager.get(alarmId);

        assertNotNull(alarm);
        assertEquals(otherLocation, alarm.getAddress());
    }
}
