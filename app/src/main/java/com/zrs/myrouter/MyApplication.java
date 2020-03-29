package com.zrs.myrouter;

import android.app.Application;

import com.zrs.router.api.core.MyRouter;

/**
 * @author zhang
 * @date 2020/3/29 0029
 * @time 12:51
 * @describe TODO
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyRouter.openDebug(); //开启debug模式  instrun 模式建议开启
        MyRouter.init(this);

    }
}
