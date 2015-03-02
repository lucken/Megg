package com.meggnify.lib;

import android.content.Context;

public class LocalContext {

	private static Context mContext;
	
	public static Context getContext() {
		return mContext;
	}
	
	public static void setContext(Context context) {
		mContext = context;
	}

}
