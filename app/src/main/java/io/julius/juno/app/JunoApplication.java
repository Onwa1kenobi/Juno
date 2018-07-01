package io.julius.juno.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class JunoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
    }
}
