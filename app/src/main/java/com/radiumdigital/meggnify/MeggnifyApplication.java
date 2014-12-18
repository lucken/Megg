package com.radiumdigital.meggnify;

import com.radiumdigital.meggnify.lib.LocalContext;

import android.app.Application;

public class MeggnifyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		LocalContext.setContext(this);
	}

}
