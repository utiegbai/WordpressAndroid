package com.entpress.entpress.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by utimac on 07/06/2018.
 */

import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.loopj.android.http.RequestParams;
import com.entpress.entpress.App;
import com.entpress.entpress.R;
import com.entpress.entpress.db.SharedPreferenceStorage;
import com.entpress.entpress.interfaces.VolleyResponse;
import com.entpress.entpress.models.UserDetails;
import com.entpress.entpress.utility.api.ApiCall;
import com.entpress.entpress.utility.api.ApiUrl;
import com.entpress.entpress.utility.data.JsonParser;
import com.entpress.entpress.utility.views.ShowAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog dialog;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    RequestParams requestParams;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login_user();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void show_progress_dialog(String message){
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void login_user(){

        if (!validate()) {
            onLoginFailed();
            return;
        }
        String username = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        try {
            requestParams = new RequestParams();
            requestParams.add("username", username);
            requestParams.add("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setMessage("login in...");
        dialog.setCancelable(false);
        dialog.show();
        Log.e("login request",String.valueOf(requestParams));
        new ApiCall().get(new VolleyResponse() {
            @Override
            public void onSuccess(String result) {

                Log.e("login result",result);
                try {
                    JSONObject response = new JSONObject(result);
                    JSONArray array = response.getJSONArray("user");
                    int jsonMsg = response.getInt("success");
                    if(jsonMsg == 1){
                        for (int i = 0; i < array.length(); i++) {
                            //get user data and store in db
                            JSONObject jsonObject = array.getJSONObject(i);
                            App.localDb.clearUserDataTable();
                            App.localDb.addUserData(JsonParser.getUser(jsonObject));

                            //go to homepage activity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }
                    } else{
                        ShowAlert.Alert(LoginActivity.this, "Error", "Login details incorrect", null, "Ok");
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("login request",e.getMessage());
                }
            }

            @Override
            public void requestError(VolleyError error) {
                dialog.dismiss();
                ShowAlert.Alert(LoginActivity.this, "Error", "Connection Error", null, "Ok");
            }
        }, ApiUrl.LOGIN_URL+requestParams);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("enter a valid user address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            _passwordText.setError("password is less than 4 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
