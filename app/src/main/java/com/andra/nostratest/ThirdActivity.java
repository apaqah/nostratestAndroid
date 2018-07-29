package com.andra.nostratest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

public class ThirdActivity extends AppCompatActivity implements PersonView.OnActionListener {
    private static final String TAG = "ThirdActivity";
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;
    ProgressBar progressBar;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        progressBar = findViewById(R.id.progressBar);
        initToolbar();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(ThirdActivity.this, AddorEdit_.class);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });
        initAdapter();
        getData();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter();
        adapter.setOnActionListener(this);
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        ApiConfig.instance().getContacts(new ApiConfig.ApiCallback<List<Contact>>() {
            @Override
            public String getKey() {
                return TAG;
            }

            @Override
            public void success(List<Contact> contacts, String messages) {
                if (contacts != null) {
                    if (contacts.size() > 0) {
                        adapter.setData(contacts);
                        mRecyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ThirdActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                mRecyclerView.setAdapter(null);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onEdit(PersonView personView) {
        Intent intent = new Intent(this, AddorEdit_.class);
        intent.putExtra("type", "edit");
        intent.putExtra("id", personView.getDatas().getId());
        intent.putExtra("name", personView.getDatas().getName());
        intent.putExtra("address", personView.getDatas().getAddress());
        intent.putExtra("phone", personView.getDatas().getPhone());
        intent.putExtra("email", personView.getDatas().getEmail());
        intent.putExtra("version", personView.getDatas().getVersion());
        startActivity(intent);
    }

    @Override
    public void onDelete(PersonView personView) {
        ApiConfig.instance().deleteContact(personView.getDatas().getId(), new ApiConfig.ApiCallback<JsonObject>() {
            @Override
            public String getKey() {
                return TAG;
            }

            @Override
            public void success(JsonObject jsonObject, String messages) {
                Toast.makeText(ThirdActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                getData();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
