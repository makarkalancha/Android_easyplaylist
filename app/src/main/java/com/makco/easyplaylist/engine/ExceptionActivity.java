package com.makco.easyplaylist.engine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ExceptionActivity extends Activity {
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_exception);

        error = (TextView) findViewById(R.id.error);
        error.setText(getIntent().getStringExtra("error"));
    }
}
