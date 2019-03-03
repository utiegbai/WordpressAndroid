package com.entpress.entpress;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.entpress.entpress.constants.Constants;
import com.google.android.gms.ads.MobileAds;


/**
 * Created by ray on 04-02-2018.
 */
public class App extends Application {

    private static Context context = null;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }


    /**
     * Because you must create the notification channel before posting any notifications on Android 8.0 and higher,
     * you should execute this code as soon as your app starts.
     * It's safe to call this repeatedly because creating an existing notification channel performs no operation.
     */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
