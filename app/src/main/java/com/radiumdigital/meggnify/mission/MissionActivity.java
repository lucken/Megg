package com.radiumdigital.meggnify.mission;

import com.radiumdigital.meggnify.Constants;
import com.radiumdigital.meggnify.R;
import com.radiumdigital.meggnify.R.id;
import com.radiumdigital.meggnify.R.layout;
import com.radiumdigital.meggnify.lib.LocalContext;
import com.radiumdigital.meggnify.model.Mission;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class MissionActivity extends ActionBarActivity implements MissionFragment.MissionTaskCallback {

    private Context context;

    private String mUserToken;

	private Mission mission;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission);

        context = LocalContext.getContext();

        mUserToken = getToken(context);

		Bundle extras = getIntent().getExtras();
		mission = extras.getParcelable("mission");
		
		setTitle(mission.name);

		if (savedInstanceState == null) {
			MissionFragment fragment = new MissionFragment();
			fragment.setArguments(extras);
			
			getSupportFragmentManager().beginTransaction()
				.add(R.id.container, fragment)
				.commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}


    private String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token  = prefs.getString(Constants.PREF_USER_TOKEN, "");
        if (token.isEmpty()) {
            return null;
        }
        return token;
    }
	@Override
	public void onSubmitMission() {
		// TODO Auto-generated method stub
		finish();
	}

}
