package com.radiumdigital.meggnify.megg;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radiumdigital.meggnify.MeggnifyManager;
import com.radiumdigital.meggnify.R;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

	private static final int FRAGMENT_POSITION = 0;

	private MeggnifyManager meggManager;

    private String mUserToken;

    TextView pendingAssignment;
    TextView earliestAssignment;

	public HomeFragment() {
		meggManager = MeggnifyManager.sharedInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        pendingAssignment = (TextView) view.findViewById(R.id.pending_assignment_text);
        earliestAssignment = (TextView) view.findViewById(R.id.earliest_assignment_text);

		loadSummary();
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		((MeggnifyActivity) activity).onFragmentAttached(FRAGMENT_POSITION);
	}
	
	void loadSummary() {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return meggManager.refreshSummary(mUserToken);
			}
			
			@Override
			protected void onPostExecute(Boolean status) {
				if (status) {
                    JSONObject summary = meggManager.summary;
                    try {
                        pendingAssignment.setText(String.format("You have %s pending missions", summary.getString("number_of_pending")));
                        earliestAssignment.setText(String.format("Your earliest assignment ends at %s", summary.getString("earliest_mission_date")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
				}
			}
			
		}.execute();
	}

}
