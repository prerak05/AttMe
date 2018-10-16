package com.attme.LoginScreen;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.attme.ForgetPasswordScreen.ForgetPassword;
import com.attme.HomeScreen.Home;
import com.attme.LoginScreen.model.Login;
import com.attme.R;
import com.attme.SignUpScreen.SignUpActivity;
import com.attme.remote.ApiService;
import com.attme.remote.Retro;
import com.attme.util.AppUtils;
import com.attme.util.ShardPref;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private TextView tv_toolbar_text;
    private EditText et_emailID, et_password;
    private TextView tv_forgetPassword, tv_signUp;
    private ImageView img_password_show_hide;
    private String str_emailId, str_password;
    private RelativeLayout relative;
    private Button btn_login;
    private ProgressBar progressBar;
    private ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();
    }

    private void init() {
        tv_toolbar_text = (TextView) findViewById(R.id.tv_toolbar_text);
        tv_toolbar_text.setText("Welcome back to ...");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MavenPro-Regular.ttf");
        et_emailID = (EditText) findViewById(R.id.et_emailID);
        et_emailID.setTypeface(typeface);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password.setTypeface(typeface);
        tv_forgetPassword = (TextView) findViewById(R.id.tv_forgetPassword);
        tv_signUp = (TextView) findViewById(R.id.tv_signUp);
        img_password_show_hide = (ImageView) findViewById(R.id.img_password_show_hide);
        btn_login = (Button) findViewById(R.id.btn_login);
        relative = findViewById(R.id.relative);
        progressBar = findViewById(R.id.progressBar);
    }

    private void onClick() {
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

        tv_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private boolean validation() {
        str_emailId = et_emailID.getText().toString();
        str_password = et_password.getText().toString();
        if (str_emailId.equals("") || str_emailId == null) {
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
        } else if (str_password == null || str_password.equals("")) {
            et_password.setError("Enter Password");
            et_password.requestFocus();
            return false;
        } else {
            if (AppUtils.isNetworkAvailable(LoginActivity.this)) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setClickable(false);
                Map<String, String> loginMap = new HashMap<>();
                loginMap.put("email", str_emailId);
                loginMap.put("password", str_password);
                mApiService = Retro.setupRetrofit(Retro.baseURL);
                Call<Login> loginCall = mApiService.loginCall(loginMap);
                Log.d("url", loginCall.request().url().toString());
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.body() != null && response.body().getOutput().equals("success")) {
                            new ShardPref(LoginActivity.this).saveValue("isLogin", "true");
                            Intent intent = new Intent(LoginActivity.this, Home.class);
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
                        progressBar.setVisibility(View.GONE);
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