package com.entpress.entpress.utility.views;

/**
 * Created by utimac on 13/02/2018.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.entpress.entpress.R;

public class ShowAlert {


    public static void Alert(final Activity activity, String title, String message, String firstButtonName, String seccondButtonName){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//		builder.setTitle(title);
        if (message != null) {
            builder.setMessage(message);
        }
        if (firstButtonName != null) {
            builder.setPositiveButton(firstButtonName,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            activity.finish();
                        }
                    });
        }

        builder.setCancelable(false)
                .setNegativeButton(seccondButtonName,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();

    }


    public static void setSnackBar(View coordinatorLayout, String snackTitle) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, snackTitle, Snackbar.LENGTH_SHORT);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public static void Notify(final Activity activity, String title, String message, String firstButtonName, String seccondButtonName){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//		builder.setTitle(title);
        if (message != null) {
            builder.setMessage(message);
        }
        if (firstButtonName != null) {
            builder.setPositiveButton(firstButtonName,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }

        builder.setCancelable(false)
                .setNegativeButton(seccondButtonName,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String url = "https://www.nigpro.com/create-listing/";
                                Uri uri = Uri.parse(url);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                activity.startActivity(intent);
                                //activity.finish();
                            }
                        }).show();

    }

}
