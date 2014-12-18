package com.radiumdigital.meggnify.megg;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radiumdigital.meggnify.MeggnifyManager;
import com.radiumdigital.meggnify.R;

public class SettingFragment extends Fragment {

	private static final int FRAGMENT_POSITION = 4;
	
	private MeggnifyManager meggManager;
	
	public SettingFragment() {
		meggManager = MeggnifyManager.sharedInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		
		loadSetting();
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MeggnifyActivity) activity).onFragmentAttached(FRAGMENT_POSITION);
	}
	
	void loadSetting() {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return meggManager.refreshSetting();
			}
			
			@Override
			protected void onPostExecute(Boolean status) {
				if (status) {
					
				} else {
					
				}
			}
			
		}.execute();
	}

}
