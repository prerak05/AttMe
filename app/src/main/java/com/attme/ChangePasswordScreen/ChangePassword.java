package com.attme.ChangePasswordScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.attme.HomeScreen.Home;
import com.attme.ProfileScreen.Profile;
import com.attme.ProfileScreen.UpdateProfile;
import com.attme.R;

public class ChangePassword extends Activity {

    private TextView tv_toolbar_text;
    private EditText et_oldPassword, et_newPassword, et_ConfirmPassword;
    private ImageView toolbar_img_1, img_password_show_hide, img_password_show_hide_1, img_password_show_hide_2;
    private String str_oldPassword, str_newPassword, str_confirmPassword;
    private Button btn_updatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();
    }

    private void init() {
        tv_toolbar_text = (TextView) findViewById(R.id.tv_toolbar_text);
        tv_toolbar_text.setText("Change Password");
        toolbar_img_1 = (ImageView) findViewById(R.id.toolbar_img_1);
        toolbar_img_1.setVisibility(View.VISIBLE);
        toolbar_img_1.setBackgroundResource(R.drawable.back_arrow);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MavenPro-Regular.ttf");
        et_oldPassword = (EditText) findViewById(R.id.et_oldPassword);
        et_oldPassword.setTypeface(typeface);
        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        et_newPassword.setTypeface(typeface);
        et_ConfirmPassword = (EditText) findViewById(R.id.et_ConfirmPassword);
        et_ConfirmPassword.setTypeface(typeface);
        img_password_show_hide = (ImageView) findViewById(R.id.img_password_show_hide);
        img_password_show_hide_1 = (ImageView) findViewById(R.id.img_password_show_hide_1);
        img_password_show_hide_2 = (ImageView) findViewById(R.id.img_password_show_hide_2);
        btn_updatePassword = (Button) findViewById(R.id.btn_updatePassword);
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
                    if (et_oldPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        et_oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        et_oldPassword.setSelection(et_oldPassword.length());
                        et_oldPassword.setTypeface(Typeface.DEFAULT);
                        img_password_show_hide.setImageResource(R.drawable.eye_hide);
                    } else {
                        et_oldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        et_oldPassword.setSelection(et_oldPassword.length());
                        et_oldPassword.setTypeface(Typeface.DEFAULT);
                        img_password_show_hide.setImageResource(R.drawable.eye_show);
                    }
                }
            }
        });

        img_password_show_hide_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.img_password_show_hide_1) {
                    if (et_newPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        et_newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        et_newPassword.setSelection(et_newPassword.length());
                        et_newPassword.setTypeface(Typeface.DEFAULT);
                        img_password_show_hide_1.setImageResource(R.drawable.eye_hide);
                    } else {
                        et_newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        et_newPassword.setSelection(et_newPassword.length());
                        et_newPassword.setTypeface(Typeface.DEFAULT);
                        img_password_show_hide_1.setImageResource(R.drawable.eye_show);
                    }
                }
            }
        });

        img_password_show_hide_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.img_password_show_hide_2) {
                    if (et_ConfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        et_ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        et_ConfirmPassword.setSelection(et_ConfirmPassword.length());
                        et_ConfirmPassword.setTypeface(Typeface.DEFAULT);
                        img_password_show_hide_2.setImageResource(R.drawable.eye_hide);
                    } else {
                        et_ConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        et_ConfirmPassword.setSelection(et_ConfirmPassword.length());
                        et_ConfirmPassword.setTypeface(Typeface.DEFAULT);
                        img_password_show_hide_2.setImageResource(R.drawable.eye_show);
                    }
                }
            }
        });

        btn_updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private boolean validation() {
        str_oldPassword = et_oldPassword.getText().toString();
        str_newPassword = et_newPassword.getText().toString();
        str_confirmPassword = et_ConfirmPassword.getText().toString();
        if (str_oldPassword.equals("") || str_oldPassword == null) {
            et_oldPassword.setError("Enter Old Password");
            et_oldPassword.requestFocus();
            return false;
        } else if (str_newPassword == null || str_newPassword.equals("")) {
            et_newPassword.setError("Enter New Password");
            et_newPassword.requestFocus();
        } else if (str_confirmPassword == null || str_confirmPassword.equals("")) {
            et_ConfirmPassword.setError("Enter Confirm Password");
            et_ConfirmPassword.requestFocus();
        } else {
            Toast.makeText(this, "Password Change Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePassword.this, Home.class);
            startActivity(intent);
        }
        return true;
    }
}