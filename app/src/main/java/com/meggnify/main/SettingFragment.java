package com.meggnify.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.helper.Constants;

public class SettingFragment extends Fragment {

    private static final int FRAGMENT_POSITION = 4;

    private IMainListener mListener;

    private MeggnifyManager meggManager;

    private String mUserToken;

    private Switch SwRinger, SwPopup;


    public SettingFragment() {
        meggManager = MeggnifyManager.sharedInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        SwRinger = (Switch) view.findViewById(R.id.SwRinger);
        SwPopup = (Switch) view.findViewById(R.id.SwPopup);
        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        loadSetting();
        SwRinger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                e.putBoolean(Constants.PREF_SETTING_RINGER, isChecked);
                e.commit();
            }
        });
        SwPopup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                e.putBoolean(Constants.PREF_SETTING_POPUP, isChecked);
                e.commit();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SwRinger.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(Constants.PREF_SETTING_RINGER, false));
        SwPopup.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(Constants.PREF_SETTING_POPUP, false));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IMainListener) activity;
            mListener.onFragmentAttached(FRAGMENT_POSITION);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement DrawerCallback.");
        }
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
