package com.attme.HomeScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.attme.HomeScreen.model.SubjectList;
import com.attme.R;
import com.attme.remote.ApiService;
import com.attme.remote.Retro;
import com.attme.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Activity {

    private TextView tv_toolbar_text;
    private ImageView toolbar_img_1, toolbar_img_2;
    private List<Subject_Get_Set> subjectGetSetList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SubjectListAdapter mAdapter;
    private RelativeLayout relative;
    private ProgressBar progressBar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();

        subjectListServiceCall();
    }

    private void subjectListServiceCall() {
        if (AppUtils.isNetworkAvailable(this)) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setClickable(false);
            apiService = Retro.setupRetrofit(Retro.baseURL);
            Call<SubjectList> subjectListCall = apiService.getSubjectListCall();
            Log.d("url", subjectListCall.request().url().toString());
            subjectListCall.enqueue(new Callback<SubjectList>() {
                @Override
                public void onResponse(Call<SubjectList> call, Response<SubjectList> response) {
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<SubjectList> call, Throwable t) {
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

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        relative = findViewById(R.id.relative);
        tv_toolbar_text = (TextView) findViewById(R.id.tv_toolbar_text);
        tv_toolbar_text.setText("Att Me");
        /*toolbar_img_1 = (ImageView) findViewById(R.id.toolbar_img_1);
        toolbar_img_1.setVisibility(View.VISIBLE);
        toolbar_img_1.setBackgroundResource(R.drawable.ic_user);*/
        toolbar_img_2 = (ImageView) findViewById(R.id.toolbar_img_2);
        toolbar_img_2.setVisibility(View.VISIBLE);
        toolbar_img_2.setBackgroundResource(R.drawable.ic_logout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SubjectListAdapter(subjectGetSetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareFileDownloadData();
    }

    private void onClick() {
        /*toolbar_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });*/

        toolbar_img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog(LayoutInflater.from(Home.this));
            }
        });
    }

    private void showExitDialog(LayoutInflater from) {
        final View forgetDialogView = from.inflate(R.layout.popup_exit, null);
        final AlertDialog forgetDialog = new AlertDialog.Builder(Home.this, R.style.NewDialog).create();
        forgetDialog.setView(forgetDialogView);
        forgetDialog.setCanceledOnTouchOutside(false);
        forgetDialog.setCancelable(false);
        forgetDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetDialog.dismiss();
                finish();
            }
        });
        forgetDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetDialog.dismiss();
            }
        });
        forgetDialog.show();
    }

    private void prepareFileDownloadData() {
        Subject_Get_Set filesGetSet = new Subject_Get_Set("Subject 1");
        subjectGetSetList.add(filesGetSet);

        filesGetSet = new Subject_Get_Set("Subject 2");
        subjectGetSetList.add(filesGetSet);

        mAdapter.notifyDataSetChanged();
    }
}