package com.meggnify;

import com.meggnify.lib.LocalContext;

import android.app.Application;

public class MeggnifyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LocalContext.setContext(this);
    }

}
