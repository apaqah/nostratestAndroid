package com.andra.nostratest;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    private TextView txTwo, txThree;
    private int result, temp, sum;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        txTwo = findViewById(R.id.txTwo);
        txThree = findViewById(R.id.txThree);
        setPowerResult();
    }

    @SuppressLint("SetTextI18n")
    private void setPowerResult() {
        result = (int) Math.pow(2, 20);
        txTwo.setText(result + "");
    }

    public void sumOfResult(View view) {
        temp = result;
        while (temp > 0) {
            count++;
            temp = temp / 10;
        }
        int i = (int) Math.pow(10, count - 1);
        for (; i > 0; i /= 10) {
            sum += result / i % 10;
        }
        if (sum > 0) {
            txThree.setText(sum + "");
        }
    }
}
