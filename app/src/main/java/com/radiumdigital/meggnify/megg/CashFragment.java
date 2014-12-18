package com.radiumdigital.meggnify.megg;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.radiumdigital.meggnify.MeggnifyManager;
import com.radiumdigital.meggnify.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CashFragment extends Fragment {

	private static final int FRAGMENT_POSITION = 2;
	
	private MeggnifyManager meggManager;

    private String mUserToken;
	
	TextView cashHarvest;
    TextView cashConsumed;
	
	public CashFragment() {
		meggManager = MeggnifyManager.sharedInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cash, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        cashHarvest = (TextView) view.findViewById(R.id.harvest_text);
        cashConsumed = (TextView) view.findViewById(R.id.consumed_text);

		loadCash(true);
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		loadCash(false);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MeggnifyActivity) activity).onFragmentAttached(FRAGMENT_POSITION);
	}
	
	void loadCash(boolean cache) {
		new AsyncTask<Boolean, Void, Boolean>() {

			private boolean cache;
			
			@Override
			protected Boolean doInBackground(Boolean... params) {
				// TODO Auto-generated method stub
				cache = params[0];
				
				return meggManager.refreshCash(cache, mUserToken);
			}
			
			@Override
			protected void onPostExecute(Boolean status) {
				if (status) {
                    JSONObject cash = meggManager.cash;
                    try {
                        cashHarvest.setText(String.format("Harvest Value ($%s.00)", cash.getString("harvest")));
                        cashConsumed.setText(String.format("Consumed  Value ($%s.00)", cash.getString("consumed")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
			}
			
		}.execute(cache);
	}

}
