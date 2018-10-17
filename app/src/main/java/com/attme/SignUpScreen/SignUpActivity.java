package com.attme.SignUpScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.attme.HomeScreen.Home;
import com.attme.LoginScreen.model.Login;
import com.attme.R;
import com.attme.remote.ApiService;
import com.attme.remote.Retro;
import com.attme.util.AppUtils;
import com.attme.util.ShardPref;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends Activity {

    private TextView tv_toolbar_text;
    private ImageView toolbar_img_1, img_password_show_hide;
    private EditText et_fullName, et_emailID, et_phoneNo, et_studentId, et_password;
    private String str_fullName, str_emailId, str_phoneNo, str_studentId, str_password;
    private Button btn_submit;
    private LinearLayout ll_ProgressBar;
    private ApiService mApiService;
    private RelativeLayout relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();
    }

    private void init() {
        tv_toolbar_text = (TextView) findViewById(R.id.tv_toolbar_text);
        tv_toolbar_text.setText("Welcome to ...");
        toolbar_img_1 = (ImageView) findViewById(R.id.toolbar_img_1);
        toolbar_img_1.setVisibility(View.VISIBLE);
        toolbar_img_1.setBackgroundResource(R.drawable.back_arrow);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MavenPro-Regular.ttf");
        et_fullName = (EditText) findViewById(R.id.et_fullName);
        et_fullName.setTypeface(typeface);
        et_emailID = (EditText) findViewById(R.id.et_emailID);
        et_emailID.setTypeface(typeface);
        et_phoneNo = (EditText) findViewById(R.id.et_phoneNo);
        et_phoneNo.setTypeface(typeface);
        et_studentId = (EditText) findViewById(R.id.et_studentId);
        et_studentId.setTypeface(typeface);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password.setTypeface(typeface);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        img_password_show_hide = (ImageView) findViewById(R.id.img_password_show_hide);
        ll_ProgressBar = (LinearLayout) findViewById(R.id.ll_ProgressBar);
        relative = findViewById(R.id.relative);
    }

    private void onClick() {
        toolbar_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_password_show_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.img_password_show_hide) {
                    if (et_password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        et_password.setSelection(et_password.length());
                        et_password.setTypeface(Typeface.DEFAULT);
                        img_password_show_hide.setImageResource(R.drawable.eye_hide);
                    } else {
                        et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        et_password.setSelection(et_password.length());
                        et_password.setTypeface(Typeface.DEFAULT);
                        img_password_show_hide.setImageResource(R.drawable.eye_show);
                    }
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private boolean validation() {
        str_fullName = et_fullName.getText().toString();
        str_emailId = et_emailID.getText().toString();
        str_phoneNo = et_phoneNo.getText().toString();
        str_studentId = et_studentId.getText().toString();
        str_password = et_password.getText().toString();
        if (str_fullName.equals("") || str_fullName == null) {
            et_fullName.setError("Enter Full Name");
            et_fullName.requestFocus();
            return false;
        } else if (str_emailId.equals("") || str_emailId == null) {
            et_emailID.setError("Enter Email ID");
            et_emailID.requestFocus();
            return false;
        } else if (str_emailId == null || str_emailId.equals("")) {
            et_emailID.setError("Please Enter Email ID");
            et_emailID.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(str_emailId).matches()) {
            et_emailID.setError("Please enter valid email address");
            et_emailID.requestFocus();
            return false;
        } else if (str_phoneNo == null || str_phoneNo.equals("")) {
            et_phoneNo.setError("Enter Phone number");
            et_phoneNo.requestFocus();
            return false;
        } else if (str_phoneNo.length() != 10) {
            et_phoneNo.setError("Please Enter 10 digit Phone number");
            et_phoneNo.requestFocus();
        } else if (str_studentId.equals("") || str_studentId == null) {
            et_studentId.setError("Enter Student ID");
            et_studentId.requestFocus();
            return false;
        } else if (str_password == null || str_password.equals("")) {
            et_password.setError("Enter Password");
            et_password.requestFocus();
            return false;
        } else {
            if (AppUtils.isNetworkAvailable(SignUpActivity.this)) {
                ll_ProgressBar.setVisibility(View.VISIBLE);
                ll_ProgressBar.setClickable(false);
                Map<String, String> registrationMap = new HashMap<>();
                registrationMap.put("name", str_fullName);
                registrationMap.put("studentid", str_studentId);
                registrationMap.put("password", str_password);
                registrationMap.put("email", str_emailId);
                registrationMap.put("phone", str_phoneNo);
                mApiService = Retro.setupRetrofit(Retro.baseURL);
                Call<Login> loginCall = mApiService.getRegistration(registrationMap);
                Log.d("url", loginCall.request().url().toString());
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        ll_ProgressBar.setVisibility(View.GONE);
                        if (response.body() != null && response.body().getOutput().equals("success")) {
                            new ShardPref(SignUpActivity.this).saveValue("isLogin", "true");
                            Intent intent = new Intent(SignUpActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(relative, getString(R.string.toast_server_error), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        ll_ProgressBar.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar
                                .make(relative, getString(R.string.failure), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            } else {
                Snackbar snackbar = Snackbar
                        .make(relative, getString(R.string.no_internet), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        return true;
    }
}