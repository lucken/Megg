package com.meggnify.main;

import com.androidquery.AQuery;
import com.meggnify.MeggnifyManager;
import com.meggnify.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {
    private static int SPLASH_TIME_OUT = 3000;
    private static final int FRAGMENT_POSITION = 3;

    private IMainListener mListener;

    private MeggnifyManager meggManager;

    private String mUserToken;

    private TextView profilePoint, profileName, profileEmail, TvRank, TvMp;

    private ImageView ImgRank;

    private FrameLayout FramePb;

    private LinearLayout LayoutProfileIntro;
    private View PbRank;

    private ProgressBar progressBar;

    public ProfileFragment() {
        meggManager = MeggnifyManager.sharedInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        ImgRank = (ImageView) view.findViewById(R.id.ImgRank);
        TvRank = (TextView) view.findViewById(R.id.TvRank);
        TvMp = (TextView) view.findViewById(R.id.TvMp);
        profilePoint = (TextView) view.findViewById(R.id.profile_point);
        profileName = (TextView) view.findViewById(R.id.profile_name);
        profileEmail = (TextView) view.findViewById(R.id.profile_email);

        FramePb = (FrameLayout) view.findViewById(R.id.FramePb);
        PbRank = (View) view.findViewById(R.id.PbRank);

        LayoutProfileIntro= (LinearLayout) view.findViewById(R.id.LayoutProfileIntro);
        loadProfile();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                LayoutProfileIntro.setVisibility(View.GONE);
            }
        }, SPLASH_TIME_OUT);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Typeface typeface = Typeface.createFromAsset(getActivity().getResources().getAssets(), "fonts/sketch_block.ttf");
        TvRank.setTypeface(typeface);
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

    void loadProfile() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                // TODO Auto-generated method stub
                return meggManager.refreshProfile();
            }

            @Override
            protected void onPostExecute(Boolean status) {
                progressBar.setVisibility(View.GONE);
                if (status) {
                    JSONObject profile = meggManager.profile;
                    try {
                        ViewGroup.LayoutParams lp = FramePb.getLayoutParams();
                        int point = profile.getInt("point");
                        TvMp.setText(point + "MP");
                        int rank = 0;
                        if (point<21)
                            rank = 1;
                        else if (point<51)
                            rank = 2;
                        else if (point<101)
                            rank = 3;
                        else if (point<501)
                            rank = 4;
                        else
                            rank = 5;
                        switch (rank){
                            case 1:
                                ImgRank.setImageDrawable(getResources().getDrawable(R.drawable.megg_baby));
                                TvRank.setText("Baby Megg");
                                FramePb.setBackgroundResource(R.drawable.pb1);
                                PbRank.setBackgroundResource(R.drawable.bg_pb1);
                                PbRank.getLayoutParams().width = (15 * (-50 + FramePb.getMeasuredWidth())) / 100;
                                point = 21-point;
                                break;
                            case 2:
                                ImgRank.setImageDrawable(getResources().getDrawable(R.drawable.megg_kiddo));
                                TvRank.setText("Megg Kiddo");
                                FramePb.setBackgroundResource(R.drawable.pb2);
                                PbRank.setBackgroundResource(R.drawable.bg_pb2);
                                PbRank.getLayoutParams().width = (30 * (-50 + FramePb.getMeasuredWidth())) / 100;
                                point = 51-point;
                                break;
                            case 3:
                                ImgRank.setImageDrawable(getResources().getDrawable(R.drawable.megg_big));
                                TvRank.setText("Big Megg");
                                FramePb.setBackgroundResource(R.drawable.pb3);
                                PbRank.setBackgroundResource(R.drawable.bg_pb3);
                                PbRank.getLayoutParams().width = (50 * (-50 + FramePb.getMeasuredWidth())) / 100;
                                point = 101-point;
                                break;
                            case 4:
                                ImgRank.setImageDrawable(getResources().getDrawable(R.drawable.megg_super));
                                TvRank.setText("Super Megg");
                                FramePb.setBackgroundResource(R.drawable.pb4);
                                PbRank.setBackgroundResource(R.drawable.bg_pb4);
                                PbRank.getLayoutParams().width = (70 * (-50 + FramePb.getMeasuredWidth())) / 100;
                                point = 501-point;
                                break;
                            case 5:
                                ImgRank.setImageDrawable(getResources().getDrawable(R.drawable.megg_royale));
                                TvRank.setText("Megg Royale");
                                FramePb.setBackgroundResource(R.drawable.pb5);
                                PbRank.setBackgroundResource(R.drawable.bg_pb5);
                                PbRank.getLayoutParams().width = (90 * (-50 + FramePb.getMeasuredWidth())) / 100;
                                break;
                        }

                        float scale = getResources().getDisplayMetrics().density;
                        int left = (int) (15*scale + 0.5f);
                        int top = (int) (13*scale + 0.5f);
                        FramePb.setPadding(left,top,0,0);

                        int progress = profile.getInt("point") * 100 / profile.getInt("next_rank_point");
                        profilePoint.setText(String.format("Megg Points (%s Points to Next Level)", point));
                        profileName.setText(String.format("Name: %s", profile.getString("full_name")));
                        profileEmail.setText(String.format("Email: %s", profile.getString("email")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.execute();
    }

}
