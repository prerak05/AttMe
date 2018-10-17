package com.attme.SubjectAttendanceScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.attme.HomeScreen.Home;
import com.attme.LoginScreen.model.Login;
import com.attme.R;
import com.attme.remote.ApiService;
import com.attme.remote.Retro;
import com.attme.util.AppUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectAttendance extends Activity {

    private TextView tv_toolbar_text, tv_subjectName;
    private ImageView toolbar_img_1, img_check_uncheck;
    private Button btn_submit;
    boolean isPressed, isSelected;
    private String sSubjectName, sId;
    private RelativeLayout relative;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_attendance);
        if (getIntent().getExtras() != null) {
            sSubjectName = getIntent().getStringExtra("subjectName");
            sId = getIntent().getStringExtra("id");
        }
        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();
    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        relative = findViewById(R.id.relative);
        tv_toolbar_text = (TextView) findViewById(R.id.tv_toolbar_text);
        tv_toolbar_text.setText("Attendances");
        toolbar_img_1 = (ImageView) findViewById(R.id.toolbar_img_1);
        toolbar_img_1.setVisibility(View.VISIBLE);
        toolbar_img_1.setBackgroundResource(R.drawable.back_arrow);
        tv_subjectName = (TextView) findViewById(R.id.tv_subjectName);
        tv_subjectName.setText(sSubjectName);
        img_check_uncheck = (ImageView) findViewById(R.id.img_check_uncheck);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }


    private void onClick() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    if (AppUtils.isNetworkAvailable(SubjectAttendance.this)) {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setClickable(false);
                        Map<String, String> map = new HashMap<>();
                        map.put("studentid", sId);
                        map.put("subjectname", sSubjectName);
                        ApiService apiService = Retro.setupRetrofit(Retro.baseURL);
                        Call<Login> attendanceCall = apiService.doAttendance(map);
                        attendanceCall.enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                progressBar.setVisibility(View.GONE);
                                if (response.body() != null) {
                                    Intent intent = new Intent(SubjectAttendance.this, Home.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                                .make(relative, getString(R.string.toast_server_error), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    img_check_uncheck.setBackgroundResource(R.drawable.uncheck_box_red);
                    Snackbar snackbar = Snackbar
                            .make(relative, getString(R.string.select_box), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        toolbar_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        img_check_uncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed) {
                    isSelected = false;
                    v.setBackgroundResource(R.drawable.uncheck_box_blue);
                } else {
                    v.setBackgroundResource(R.drawable.check_box_green);
                    isSelected = true;
                }
                isPressed = !isPressed; // reverse
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
