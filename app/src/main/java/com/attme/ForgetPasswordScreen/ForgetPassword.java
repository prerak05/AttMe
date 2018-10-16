package com.attme.ForgetPasswordScreen;

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

import com.attme.LoginScreen.LoginActivity;
import com.attme.R;

public class ForgetPassword extends Activity {

    private TextView tv_toolbar_text;
    private EditText et_emailID;
    private ImageView toolbar_img_1;
    private String str_emailId;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();
    }

    private void init() {
        tv_toolbar_text = (TextView) findViewById(R.id.tv_toolbar_text);
        tv_toolbar_text.setText("Forget Password");
        toolbar_img_1 = (ImageView) findViewById(R.id.toolbar_img_1);
        toolbar_img_1.setVisibility(View.VISIBLE);
        toolbar_img_1.setBackgroundResource(R.drawable.back_arrow);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MavenPro-Regular.ttf");
        et_emailID = (EditText) findViewById(R.id.et_emailID);
        et_emailID.setTypeface(typeface);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    private void onClick() {
        toolbar_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
        str_emailId = et_emailID.getText().toString();
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
        } else {
            Toast.makeText(this, "Email Successfully Sent", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgetPassword.this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }
}