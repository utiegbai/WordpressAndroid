package com.entpress.entpress.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.entpress.entpress.R;
import com.entpress.entpress.constants.Constants;
import com.entpress.entpress.utility.localmessagemanager.LocalMessageManager;

import java.util.Objects;

/**
 * Created by ray on 07/02/2018.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Objects.equals(intent.getAction(), Constants.CANCEL_FOREGROUND_UPLOAD_ACTION)){
            Log.e("action","cancel upload reciever called");
            int notification_id = intent.getIntExtra("NOTIFICATION_ID",0);
            Log.e("action",String.valueOf(notification_id));
            LocalMessageManager.getInstance().send(R.id.cancelCurrentUploadAction,notification_id);
        }
        if(Objects.equals(intent.getAction(), Constants.RETRY_FOREGROUND_UPLOAD_ACTION)){
            Log.e("action","retry upload reciever called");
            int notification_id = intent.getIntExtra("NOTIFICATION_ID",0);
            Log.e("action",String.valueOf(notification_id));
            LocalMessageManager.getInstance().send(R.id.retryCurrentUploadAction,notification_id);
        }
        if(Objects.equals(intent.getAction(), Constants.RETRY_FOREGROUND_PUBLISH_MOMENT_ACTION)){
            Log.e("action","retry publish moment reciever called");
            int notification_id = intent.getIntExtra("NOTIFICATION_ID",0);
            Log.e("action",String.valueOf(notification_id));
            LocalMessageManager.getInstance().send(R.id.retryPublishMoment,notification_id);
        }
        if(Objects.equals(intent.getAction(), Constants.CANCEL_FOREGROUND_PUBLISH_MOMENT_ACTION)){
            Log.e("action","cancel publish moment reciever called");
            int notification_id = intent.getIntExtra("NOTIFICATION_ID",0);
            Log.e("action",String.valueOf(notification_id));
            LocalMessageManager.getInstance().send(R.id.cancelPublishMoment,notification_id);
        }
    }
}

