package com.bruce.simple;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bruce.touch.Touch3DView;
import com.bruce.touch.shadowLayout.ZDepth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Touch3DView touch3DView = (Touch3DView) findViewById(R.id.touch_view);
    }
}
