package com.attme.LoginScreen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by prerak on 16/10/18.
 */

public class Login {
    @SerializedName("output")
    @Expose
    private String output;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
