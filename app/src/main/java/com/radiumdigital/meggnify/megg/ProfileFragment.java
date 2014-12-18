package com.radiumdigital.meggnify.megg;

import com.androidquery.AQuery;
import com.radiumdigital.meggnify.MeggnifyManager;
import com.radiumdigital.meggnify.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

	private static final int FRAGMENT_POSITION = 3;

	private MeggnifyManager meggManager;

    private String mUserToken;

    AQuery mQuery;

    TextView profilePoint;
    TextView profileName;
    TextView profileEmail;
    ImageView profileRank;
    ProgressBar profileProgress;

	public ProfileFragment() { meggManager = MeggnifyManager.sharedInstance(); }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        mQuery = new AQuery(getActivity(), view);

        profileProgress = (ProgressBar) view.findViewById(R.id.progress);
        profileRank = (ImageView) view.findViewById(R.id.image_rank);
        profilePoint = (TextView) view.findViewById(R.id.profile_point);
        profileName = (TextView) view.findViewById(R.id.profile_name);
        profileEmail = (TextView) view.findViewById(R.id.profile_email);

		loadProfile(true);
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		loadProfile(false);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MeggnifyActivity) activity).onFragmentAttached(FRAGMENT_POSITION);
	}
	
	void loadProfile(boolean cache) {
		new AsyncTask<Boolean, Void, Boolean>() {

			private boolean cache;
			
			@Override
			protected Boolean doInBackground(Boolean... params) {
				// TODO Auto-generated method stub
				cache = params[0];

                return meggManager.refreshProfile(cache, mUserToken);
			}
			
			@Override
			protected void onPostExecute(Boolean status) {
				if (status) {
                    JSONObject profile = meggManager.profile;
                    try {
                        mQuery.id(R.id.image_rank).image("http://beta.meggnify.com" + profile.getString("rank_pict"));
                        int progress = profile.getInt("point") * 100 / profile.getInt("next_rank_point");
                        profileProgress.setProgress(progress);
                        profilePoint.setText(String.format("Megg Points (%s Points)", profile.getString("point")));
                        profileName.setText(String.format("Name: %s", profile.getString("full_name")));
                        profileEmail.setText(String.format("Email: %s", profile.getString("email")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
				}
			}
			
		}.execute(cache);
	}

}
