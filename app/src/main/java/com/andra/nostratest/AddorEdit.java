package com.andra.nostratest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_addor_edit)
public class AddorEdit extends AppCompatActivity {
    String secure_id, type, name, email, address, phone;
    int version;
    @ViewById
    EditText etFullName;
    @ViewById
    EditText etEmail;
    @ViewById
    EditText etPhoneNumber;
    @ViewById
    EditText etAddress;
    @ViewById
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addor_edit);
    }


    private void handleIntent() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("edit")) {
            secure_id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            phone = intent.getStringExtra("phone");
            email = intent.getStringExtra("email");
            address = intent.getStringExtra("address");
            version = intent.getIntExtra("version", 0);

            setUI();
        }
    }

    private void setUI() {
        etFullName.setText(name);
        etEmail.setText(email);
        etAddress.setText(address);
        etPhoneNumber.setText(phone);
    }

    @Click(R.id.updateOrSave)
    public void sendData() {
        if (type.equals("edit")) {
            updateData(secure_id, tempData());
        } else {
            saveData(tempData());
        }
    }

    private void updateData(String secure_id, JsonObject jsonObject) {
        progressBar.setVisibility(View.VISIBLE);
        ApiConfig.instance().updateProfile(secure_id, jsonObject, new ApiConfig.ApiCallback<JsonObject>() {
            @Override
            public String getKey() {
                return null;
            }

            @Override
            public void success(JsonObject jsonObject, String messages) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddorEdit.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddorEdit.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(JsonObject jsonObject) {
        progressBar.setVisibility(View.VISIBLE);
        ApiConfig.instance().addContact(jsonObject, new ApiConfig.ApiCallback<JsonObject>() {
            @Override
            public String getKey() {
                return null;
            }

            @Override
            public void success(JsonObject jsonObject, String messages) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddorEdit.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddorEdit.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private JsonObject tempData() {
        JsonObject obj = new JsonObject();

        obj.addProperty("name", etFullName.getText().toString());
        obj.addProperty("email", etEmail.getText().toString());
        obj.addProperty("phone", etPhoneNumber.getText().toString());
        obj.addProperty("address", etAddress.getText().toString());
        if (type.equals("edit")) {
            obj.addProperty("version", version);
        }
        return obj;
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleIntent();
    }
}
