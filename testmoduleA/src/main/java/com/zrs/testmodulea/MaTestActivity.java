package com.zrs.testmodulea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zrs.router.annotation.Route;

@Route(path = "/testmoduleA/MaTestActivity")
public class MaTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_test);
    }
}
