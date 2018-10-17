package com.attme.HomeScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.attme.HomeScreen.model.SubjectList;
import com.attme.LoginScreen.LoginActivity;
import com.attme.LoginScreen.model.Login;
import com.attme.R;
import com.attme.SubjectAttendanceScreen.SubjectAttendance;
import com.attme.remote.ApiService;
import com.attme.remote.Retro;
import com.attme.util.AppUtils;
import com.attme.util.ShardPref;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private SubjectList subject;
    private List<SubjectList> subjectLists = new ArrayList<>();
    private RecyclerView recyclerView, recyclerHorizontal;
    private SubjectListAdapter mAdapter;
    private RelativeLayout relative;
    private ProgressBar progressBar;
    private ApiService apiService;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HorizontalSubjectAdapter horizontalSubjectAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        // Todo :- Initialization
        init();

        // Todo :- onClick
        onClick();

        subjectListServiceCall();
        initSwipeToRefresh();
        initHorizontalList();
    }

    private void initHorizontalList() {
        recyclerHorizontal.setHasFixedSize(true);
        recyclerHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        horizontalSubjectAdapter = new HorizontalSubjectAdapter(this);
        recyclerHorizontal.setAdapter(horizontalSubjectAdapter);
    }

    private void initSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                subjectLists.clear();
                subjectListServiceCall();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private SubjectListAdapter.RecyclerItemClickListener itemClickListener = new SubjectListAdapter.RecyclerItemClickListener() {
        @Override
        public void onItemClick(final SubjectList subjectList1) {
            if (AppUtils.isNetworkAvailable(Home.this)) {
                Map<String, String> map = new HashMap<>();
                map.put("studentid", subjectList1.getId().toString());
                map.put("subjectname", subjectList1.getName());
                apiService = Retro.setupRetrofit(Retro.baseURL);
                Call<Login> registerCall = apiService.doRegisterSubject(map);
                Log.d("url", registerCall.request().url().toString());
                registerCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.body() != null && response.body().getOutput().equals("success")) {
                            Intent intent = new Intent(Home.this, SubjectAttendance.class);
                            intent.putExtra("subjectName", subjectList1.getName());
                            intent.putExtra("id", subjectList1.getId().toString());
                            startActivity(intent);
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(relative, getString(R.string.toast_server_error), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
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
        }
    };

    private void subjectListServiceCall() {
        if (AppUtils.isNetworkAvailable(this)) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setClickable(false);
            apiService = Retro.setupRetrofit(Retro.baseURL);
            Call<List<SubjectList>> subjectListCall = apiService.getSubjectListCall();
            Log.d("url", subjectListCall.request().url().toString());
            subjectListCall.enqueue(new Callback<List<SubjectList>>() {
                @Override
                public void onResponse(Call<List<SubjectList>> call, Response<List<SubjectList>> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.body() != null) {
//                        Type collectionType = new TypeToken<List<SubjectList>>(){}.getType();
//                        List<SubjectList> lcs = (List<SubjectList>) new Gson()
//                                .fromJson(response.body() , collectionType);
//                        subject = response.body();
                        subjectLists = response.body();
                        mAdapter = new SubjectListAdapter(subjectLists, itemClickListener);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(relative, getString(R.string.toast_server_error), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<List<SubjectList>> call, Throwable t) {
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
        recyclerHorizontal = findViewById(R.id.recyclerHorizontal);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
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
                new ShardPref(Home.this).clear();
                Intent intent = new Intent(Home.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
}