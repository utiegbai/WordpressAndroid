package com.entpress.entpress.activities;

/**
 * Created by utimac on 07/06/2018.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.entpress.entpress.App;
import com.entpress.entpress.models.UserDetails;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            // close splash activity
            finish();
    }
}
