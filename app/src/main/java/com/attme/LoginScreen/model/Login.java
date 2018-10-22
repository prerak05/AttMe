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
    @SerializedName("studentid")
    @Expose
    private String studentid;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
}
