package com.entpress.entpress.constants;

import android.Manifest;

/**
 * Created by link on 08/02/2018.
 */

public class Constants {
    public static String APP_TAG = "Entpress";
    public static String PHOTO = "photo";
    public static String CHANNEL_ID = "1234567890"; //update to something more useful
    public  static String STARTFOREGROUND_ACTION = "com.entpress.entpress.action.startuploadforeground";
    public  static String CANCEL_FOREGROUND_UPLOAD_ACTION = "com.entpress.entpress.action.canceluploadforeground";
    public  static String RETRY_FOREGROUND_UPLOAD_ACTION = "com.entpress.entpress.action.retryuploadforeground";
    public  static String RETRY_FOREGROUND_PUBLISH_MOMENT_ACTION = "com.entpress.entpress.action.retrypublishmomentforeground";
    public  static String CANCEL_FOREGROUND_PUBLISH_MOMENT_ACTION = "com.entpress.entpress.action.cancelpublishmomentforeground";
    public static int publishMomentTimeout = 3000;

    public interface PERMISSIONS{
        String[] SET_AVATAR_PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        String[] CREATE_MOMENTS_PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO};
        int REQUEST_SET_AVATAR_CODE =1;
        int REQUEST_CREATE_MOMENTS_CODE =2;
        int SMS_PERMISSION_CODE = 0;
        int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 101;
        int GALLERY_CODE = 201;
        int UPDATE_EMAIL_ACTIVITY_RESULT = 301;
        int SET_PROFILE_AVATAR_ACTIVITY_RESULT = 401;
    }

    public interface PREFS{
        String USER_REGISTRATION_STEP = "user_registration_step";
        String USER_DATA = "user_data";
        String USER_INTERESTS = "user_interests";
        String GCM_TOKEN = "gcm_token";
    }

}
