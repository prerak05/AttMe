package com.attme.ProfileScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.attme.ChangePasswordScreen.ChangePassword;
import com.attme.R;

public class UpdateProfile extends Activity {

    private TextView tv_toolbar_text, tv_changePWD;
    private ImageView toolbar_img_1;
    private EditText et_fullName, et_emailID, et_phoneNo, et_studentId;
    private String str_fullName, str_emailId, str_phoneNo, str_studentId;
    private Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile_screen);

        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();
    }

    private void init() {
        tv_toolbar_text = (TextView) findViewById(R.id.tv_toolbar_text);
        tv_toolbar_text.setText("Update Profile");
        toolbar_img_1 = (ImageView) findViewById(R.id.toolbar_img_1);
        toolbar_img_1.setVisibility(View.VISIBLE);
        toolbar_img_1.setBackgroundResource(R.drawable.back_arrow);
        tv_changePWD = (TextView) findViewById(R.id.tv_changePWD);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MavenPro-Regular.ttf");
        et_fullName = (EditText) findViewById(R.id.et_fullName);
        et_fullName.setTypeface(typeface);
        et_emailID = (EditText) findViewById(R.id.et_emailID);
        et_emailID.setTypeface(typeface);
        et_phoneNo = (EditText) findViewById(R.id.et_phoneNo);
        et_phoneNo.setTypeface(typeface);
        et_studentId = (EditText) findViewById(R.id.et_studentId);
        et_studentId.setTypeface(typeface);
        btn_update = (Button) findViewById(R.id.btn_update);
    }

    private void onClick() {
        toolbar_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_changePWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfile.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
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
        } else {
            Toast.makeText(this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfile.this, Profile.class);
            startActivity(intent);
        }
        return true;
    }
}