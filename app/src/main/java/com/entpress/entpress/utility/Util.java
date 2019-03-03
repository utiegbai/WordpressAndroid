package com.entpress.entpress.utility;

/**
 * Created by utimac on 11/06/2018.
 */
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.jsoup.Jsoup;

public class Util {

    public static void requestPermission(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
        }
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
