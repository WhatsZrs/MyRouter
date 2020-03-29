package com.zrs.myrouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zrs.router.api.core.MyRouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyRouter.getInstance().build("/testmoduleA/MaTestActivity")
                .withString("username", "qiangzi")
                .withInt("userId", 1234)
                .withBoolean("isLogin", false)
                .navigation(this);
    }

}
