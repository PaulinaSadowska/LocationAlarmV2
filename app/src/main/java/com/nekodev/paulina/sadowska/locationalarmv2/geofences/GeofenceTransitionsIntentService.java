/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nekodev.paulina.sadowska.locationalarmv2.geofences;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.nekodev.paulina.sadowska.locationalarmv2.Constants;
import com.nekodev.paulina.sadowska.locationalarmv2.Keys;
import com.nekodev.paulina.sadowska.locationalarmv2.R;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmDetails.AlarmTypes;
import com.nekodev.paulina.sadowska.locationalarmv2.alarmList.AlarmListActivity;
import com.nekodev.paulina.sadowska.locationalarmv2.data.AlarmDataItem;
import com.nekodev.paulina.sadowska.locationalarmv2.data.DataManager;

import java.util.Calendar;
import java.util.List;

/**
 * Listener for geofence transition changes.
 *
 * Receives geofence transition events from Location Services in the form of an Intent containing
 * the transition type and geofence id(s) that triggered the transition. Creates a notification
 * as the output.
 */
public class GeofenceTransitionsIntentService extends IntentService {


    protected static final String TAG = "GeofenceTransitionsIS";

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public GeofenceTransitionsIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Handles incoming intents.
     * @param intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode()));
            return;
        }
        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            for (Geofence triggeringGeofence : triggeringGeofences) {
                triggerAction(triggeringGeofence);
            }

            // Send notification and log the transition details.
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
        }
    }

    private void triggerAction(Geofence triggeringGeofence) {
        int alarmId = Integer.parseInt(triggeringGeofence.getRequestId());
        DataManager manager = DataManager.getInstance(getFilesDir().getPath(), Constants.FILE_NAME);

        AlarmDataItem alarm = manager.get(alarmId);
        boolean trigger;

        if(!alarm.shouldBeTriggered()) {
            return;
        }

        if (alarm.getRepeatDaysCount() == 0) {
            manager.editAlarmIsActive(alarm.getAlarmId(), false);
            trigger = true;
        } else {
            Calendar today = Calendar.getInstance();
            int dayOfWeek = (today.get(Calendar.DAY_OF_WEEK) + 5) % 7;
            trigger = alarm.getRepeatDays()[dayOfWeek];
        }
        if (trigger) {
            if (alarm.getAlarmType() == AlarmTypes.NOTIFICATION) {
                sendNotification(alarm);
            } else {
                triggerAlarm(alarm);
            }
        }
    }

    private void triggerAlarm(AlarmDataItem alarm) {
        Intent i = new Intent();
        i.setClassName("com.nekodev.paulina.sadowska.locationalarmv2", "com.nekodev.paulina.sadowska.locationalarmv2.AlarmActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Keys.AlarmDetailsKeys.ALARM_LOCATION_ADDRESS, alarm.getAddress());
        if(!alarm.getAlarmTone().contains("known") && alarm.getAddress() != null) {
            i.putExtra(Keys.AlarmDetailsKeys.ALARM_TONE_ADDRESS, alarm.getAlarmToneAddress());
        }
        getApplicationContext().startActivity(i);
    }

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private void sendNotification(AlarmDataItem alarm) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), AlarmListActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(AlarmListActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Uri sound;
        if (alarm.getAlarmToneAddress() == null) {
            sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        } else {
            sound = Uri.parse(alarm.getAlarmToneAddress());
        }

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_place_white_24dp)
                .setSound(sound)
                .setContentTitle(alarm.getAddress())
                .setContentText(getString(R.string.geofence_transition_notification_text))
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }
}
