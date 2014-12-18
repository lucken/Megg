package com.radiumdigital.meggnify.megg;

import com.radiumdigital.meggnify.R;
import com.radiumdigital.meggnify.mission.MissionActivity;
import com.radiumdigital.meggnify.model.Mission;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AssignmentMissionFragment extends Fragment {

    private String mUserToken;

    private String type;
	private Mission mission;
	
	private LinearLayout actionContainer;
	
	private TextView nameView;
	private TextView locationView;
	private TextView typeView;
	private TextView costView;
	private TextView endDateView;
	private TextView endTimeView;
	private TextView objectiveView;
	
	public AssignmentMissionFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_assignment_mission, container, false);
		
		Bundle extras = getArguments();
        mUserToken = extras.getString("token");
		type = extras.getString("type");
		mission = extras.getParcelable("mission");
		
		actionContainer = (LinearLayout) view.findViewById(R.id.action_container);
		
		nameView = (TextView) view.findViewById(R.id.mission_name);
		nameView.setText(mission.name);
		
		locationView = (TextView) view.findViewById(R.id.mission_location);
		locationView.setText(mission.location);
		
		typeView = (TextView) view.findViewById(R.id.mission_type);
		typeView.setText(mission.type);
		
		costView = (TextView) view.findViewById(R.id.mission_cost);
		costView.setText("$" + String.valueOf(mission.cost));
		
		endDateView = (TextView) view.findViewById(R.id.mission_end_date);
		endDateView.setText(String.valueOf(mission.endDate));
		
		endTimeView = (TextView) view.findViewById(R.id.mission_end_time);
		endTimeView.setText(String.valueOf(mission.endTime));
		
		objectiveView = (TextView) view.findViewById(R.id.mission_objective);
		objectiveView.setText(mission.objective);
		
		if (type.equals("my_jobs")) {
			Button startButton = new Button(getActivity());
			startButton.setTextColor(Color.WHITE);
			startButton.setText("Start");
			startButton.setTextSize(14);
			startButton.setBackgroundColor(Color.parseColor("#f7da00"));
			startButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
			startButton.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), MissionActivity.class);
					intent.putExtra("mission", mission);
					startActivity(intent);
				}
				
			});
			actionContainer.addView(startButton);
		} else {
			Button backButton = new Button(getActivity());
			backButton.setText("Back");
			backButton.setTextColor(Color.WHITE);
			backButton.setTextSize(14);
			backButton.setBackgroundColor(Color.parseColor("#333333"));
			backButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
			backButton.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), MissionActivity.class);
					intent.putExtra("mission", mission);
					startActivity(intent);
				}
				
			});
			actionContainer.addView(backButton);
			
			Button acceptButton = new Button(getActivity());
			acceptButton.setText("Accept");
			acceptButton.setTextColor(Color.WHITE);
			acceptButton.setTextSize(14);
			acceptButton.setBackgroundColor(Color.parseColor("#f7da00"));
			acceptButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
			acceptButton.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					
				}
				
			});
			actionContainer.addView(acceptButton);
		}
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
	}

}
