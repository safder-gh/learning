package com.example.hammadhanif.availabilityapplication.Utilities;

import android.app.NotificationManager;
import android.content.Context;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyNotificationManager  {
    private static NotificationManager notificationManager = null;

    public static NotificationManager getInstance(Context context) {
        if(notificationManager == null)
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        return notificationManager;
    }
}
