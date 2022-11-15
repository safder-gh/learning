package com.example.hammadhanif.availabilityapplication.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;


import com.example.hammadhanif.availabilityapplication.CaseSearch;
import com.example.hammadhanif.availabilityapplication.CasesRecyclerView;
import com.example.hammadhanif.availabilityapplication.MyAccountsRecyclerView;
import com.example.hammadhanif.availabilityapplication.R;
import com.example.hammadhanif.availabilityapplication.Utilities.Common;
import com.example.hammadhanif.availabilityapplication.Utilities.MyNotificationManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PendingConsultsService extends FirebaseMessagingService {
    NotificationManager notificationManager;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String message = data.get("message");
            String caseId = data.get("caseId");
            sendMyNotification(message,caseId);
        } else if (remoteMessage.getNotification() != null) {
            sendMyNotification(remoteMessage.getNotification().getBody(),"");
        }
    }
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Common.fcmTokenRefreshed = true;
        Log.d("Token",token);
        //fe49yQx2USg:APA91bF-vWayw-xgftJ2SvniW24gbHJyccxSkMcFnbaKTMxP7c3Kl9UPV4omfouBICLyBNXzxc71thSeMrAkXhvqy1Sb8JwtyY0ig5DNmL_hYjecqTdx8zZyg5M5MflLvuXIiIk0JOA9
    }
    private void sendMyNotification(String message,String caseId) {

        //On click of notification it redirect to this Activity
        /*Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);*/

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "pending_consults_id";
        CharSequence channelName = "Pending Consults";
        int importance = NotificationManager.IMPORTANCE_LOW;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.company_logo)
                .setContentTitle("Pending Consults")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri);
        Intent notificationIntent = new Intent(getApplicationContext(), CasesRecyclerView.class);
        if(!TextUtils.isEmpty(caseId)){
            Bundle bundle = new Bundle();
            bundle.putString(this.getResources().getString(R.string.queryWhere),"(cases.id='"+caseId+"' and cases_cstm.archive_c=0)");
            bundle.putString(this.getResources().getString(R.string.searchType), com.example.hammadhanif.availabilityapplication.Utilities.Common.SearchType.PendingConsult.name());
            notificationIntent.putExtras(bundle);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify((int)System.currentTimeMillis(), notificationBuilder.build());
    }
}

/*

public class MyFirebaseMessagingService extends FirebaseMessagingService {
@Override
public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
}

}
* */