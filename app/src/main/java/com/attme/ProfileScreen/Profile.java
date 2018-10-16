package com.attme.ProfileScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.attme.ForgetPasswordScreen.ForgetPassword;
import com.attme.R;

public class Profile extends Activity {

    private TextView tv_toolbar_text, tv_userName, tv_studentId, tv_className, tv_att_percentage;
    private ImageView profile_image, toolbar_img_1, toolbar_img_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();
    }

    private void init() {
        tv_toolbar_text = (TextView) findViewById(R.id.tv_toolbar_text);
        tv_toolbar_text.setText("Profile");
        toolbar_img_1 = (ImageView) findViewById(R.id.toolbar_img_1);
        toolbar_img_1.setVisibility(View.VISIBLE);
        toolbar_img_1.setBackgroundResource(R.drawable.back_arrow);
        /*toolbar_img_2 = (ImageView) findViewById(R.id.toolbar_img_2);
        toolbar_img_2.setVisibility(View.VISIBLE);
        toolbar_img_2.setBackgroundResource(R.drawable.ic_edit);*/
        profile_image = (ImageView) findViewById(R.id.profile_image);
        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_studentId = (TextView) findViewById(R.id.tv_studentId);
        tv_className = (TextView) findViewById(R.id.tv_className);
        tv_att_percentage = (TextView) findViewById(R.id.tv_att_percentage);
    }

    private void onClick() {
        toolbar_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*toolbar_img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, UpdateProfile.class);
                startActivity(intent);
            }
        });*/
    }
}