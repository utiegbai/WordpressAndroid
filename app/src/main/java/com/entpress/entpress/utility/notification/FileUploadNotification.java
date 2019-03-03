package com.entpress.entpress.utility.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.entpress.entpress.R;
import com.entpress.entpress.constants.Constants;
import com.entpress.entpress.service.NotificationReceiver;

/**
 * Created by ray on 23/02/2018.
 */

public class FileUploadNotification {
    private static NotificationManager mNotificationManager;
    private static NotificationCompat.Builder builder;
    private Context context;

    public FileUploadNotification(Context context, int NOTIFICATION_ID, boolean retry) {
        this.context = context;
       init(context,NOTIFICATION_ID,retry);
    }

    private void init(Context context, int NOTIFICATION_ID, boolean retry){
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent cancelIntent = new Intent(context, NotificationReceiver.class);
        cancelIntent.setAction(Constants.CANCEL_FOREGROUND_UPLOAD_ACTION);
        cancelIntent.putExtra("NOTIFICATION_ID",NOTIFICATION_ID);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent retryIntent = new Intent(context, NotificationReceiver.class);
        retryIntent.setAction(Constants.RETRY_FOREGROUND_UPLOAD_ACTION);
        retryIntent.putExtra("NOTIFICATION_ID",NOTIFICATION_ID);
        PendingIntent retryPendingIntent =
                PendingIntent.getBroadcast(context, 0, retryIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setProgress(100, 0, false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                //.setDeleteIntent(pendingIntent)
                .addAction(R.drawable.ic_cancel_black_24dp, "cancel",
                        pendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void updateNotification(int NOTIFICATION_ID, String percent, String fileName, String contentText) {
        try {
            builder.setContentText(contentText)
                    .setContentTitle(fileName)
                    //.setSmallIcon(android.R.drawable.stat_sys_download)
                    .setOngoing(true)
                    .setContentInfo(percent + "%")
                    .setProgress(100, Integer.parseInt(percent), false);

            mNotificationManager.notify(NOTIFICATION_ID, builder.build());
            if (Integer.parseInt(percent) == 100)
                finishingUpload(NOTIFICATION_ID);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("Error...Notification.", e.getMessage() + ".....");
            e.printStackTrace();
        }
    }

    public void failUploadNotification(/*int percentage, String fileName*/int NOTIFICATION_ID) {
        Log.e("download", "failed to download item...");

       /* Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);*/

        if (builder != null) {
       /* if (percentage < 100) {*/
            /*builder.setContentText("Uploading Failed")
                    //.setContentTitle(fileName)
                    .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                    .setOngoing(false);
            mNotificationManager.notify(NOTIFICATION_ID, builder.build());*/
            //deleteNotification(NOTIFICATION_ID);
            init(context,NOTIFICATION_ID,true);
        /*} else {
            mNotificationManager.cancel(NOTIFICATION_ID);
            builder = null;
        }*/
        } else {
            mNotificationManager.cancel(NOTIFICATION_ID);
        }
    }

    private void finishingUpload(int NOTIFICATION_ID) {
        builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID);
        builder.setContentTitle("Your moment upload was successful")
                .setContentText("please wait")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void deleteNotification(int NOTIFICATION_ID) {
        mNotificationManager.cancel(NOTIFICATION_ID);
        builder = null;
    }
}