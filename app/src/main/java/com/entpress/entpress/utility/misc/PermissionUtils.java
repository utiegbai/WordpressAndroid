package com.entpress.entpress.utility.misc;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by ray on 06/02/2018.
 */

public class PermissionUtils {


    /**
     * Runtime permission
     */
    public static boolean hasReadSmsPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }


}
