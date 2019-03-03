package com.entpress.entpress.activities;

/**
 * Created by utimac on 07/06/2018.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.loopj.android.http.RequestParams;
import com.entpress.entpress.App;
import com.entpress.entpress.R;
import com.entpress.entpress.interfaces.VolleyResponse;
import com.entpress.entpress.utility.api.ApiCall;
import com.entpress.entpress.utility.api.ApiUrl;
import com.entpress.entpress.utility.data.JsonParser;
import com.entpress.entpress.utility.views.ShowAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity{
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_f_name) EditText _fnameText;
    @BindView(R.id.input_l_name) EditText _lnameText;
    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_email) EditText _emailText;
    //@BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    RequestParams requestParams;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_user();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void signup_user(){

        if (!validate()) {
            onSignupFailed();
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(SignupActivity.this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Creating Account...");
        dialog.show();

        String fname = _fnameText.getText().toString();
        String lname = _lnameText.getText().toString();
        String full_name = fname +" "+lname;
        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        //String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        try {
            requestParams = new RequestParams();
            requestParams.add("username", username);
            requestParams.add("email", email);
            requestParams.add("password", password);
            requestParams.add("fullname", URLEncoder.encode(full_name, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("login request",String.valueOf(requestParams));
        new ApiCall().get(new VolleyResponse() {
            @Override
            public void onSuccess(String result) {
                // progressDialog.dismiss();

                Log.e("signup result",result);
                try {
                    JSONObject response = new JSONObject(result);
                    JSONArray array = response.getJSONArray("user");
                    int jsonMsg = response.getInt("success");

                    if(jsonMsg == 1){
                        Toast.makeText(SignupActivity.this,"Account successfully created!",Toast.LENGTH_SHORT).show();
                        //go to homepage activity
                        dialog.dismiss();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        ShowAlert.Alert(SignupActivity.this, "Error", "Error creating account", null, "Ok");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Log request",e.getMessage());
                }
            }

            @Override
            public void requestError(VolleyError error) {
                dialog.dismiss();
                ShowAlert.Alert(SignupActivity.this, "Error", "Connection Error", null, "Ok");
            }
        }, ApiUrl.SIGNUP_URL+requestParams);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String fname = _fnameText.getText().toString();
        String lname = _lnameText.getText().toString();
        String address = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        //String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (fname.isEmpty() || fname.length() < 3) {
            _fnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _fnameText.setError(null);
        }

        if (lname.isEmpty() || lname.length() < 3) {
            _lnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _lnameText.setError(null);
        }

        if (address.isEmpty()) {
            _usernameText.setError("Enter Valid Address");
            valid = false;
        } else {
            _usernameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        /*if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }*/

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}
