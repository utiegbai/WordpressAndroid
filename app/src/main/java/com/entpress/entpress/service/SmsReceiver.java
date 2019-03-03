package com.entpress.entpress.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.entpress.entpress.interfaces.SmsListener;

/**
 * Created by ray on 07/02/2018.
 */
public class SmsReceiver extends BroadcastReceiver {

    //interface
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        assert data != null;
        Object[] pdus = (Object[]) data.get("pdus");

        assert pdus != null;
        for (Object pdu : pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
            String sender = smsMessage.getDisplayOriginatingAddress();
            //Check the sender to filter messages which we require to read
            //Log.e("Message",smsMessage.getMessageBody());
            //Toast.makeText(context,"Message: "+smsMessage.getMessageBody(),Toast.LENGTH_LONG).show();
            if (sender.equals("Yanpals")) {
                String messageBody = smsMessage.getMessageBody();
                //Pass the message text to interface
                mListener.messageReceived(messageBody);

            }
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}

