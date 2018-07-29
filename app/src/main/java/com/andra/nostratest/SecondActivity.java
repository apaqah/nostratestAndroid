package com.andra.nostratest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private EditText edtOne, edtTwo;
    ArrayList<String> list = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        edtOne = findViewById(R.id.edtOne);
        edtTwo = findViewById(R.id.edtTwo);
        listView = findViewById(R.id.listview);
    }

    public void createList(View view) {
        list.clear();
        int x, j;
        x = Integer.parseInt(edtOne.getText().toString());
        j = Integer.parseInt(edtTwo.getText().toString());

        for (; x <= j; x++) {
            if (x % 3 == 0) {
                if (x % 4 == 0) {
                    list.add("!yeay");
                } else {
                    list.add(x + "");
                }
            } else if (x % 4 == 0) {
                list.add(x + "");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(adapter);
    }
}
