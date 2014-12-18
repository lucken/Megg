package com.radiumdigital.meggnify.megg;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.radiumdigital.meggnify.R;

public class AssignmentFragment extends Fragment {

    private String mUserToken;

	private static final int FRAGMENT_POSITION = 1;
	
	private static final int MY_JOBS = 0;
	private static final int NEW_JOBS = 1;
	private static final int MAP = 2;
	
	private Button myButton;
	private Button newButton;
	private Button mapButton;
	
	public AssignmentFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

		myButton = (Button) view.findViewById(R.id.button_my_job);
		myButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				selectItem(MY_JOBS);
			}
			
		});
		
		newButton = (Button) view.findViewById(R.id.button_new_job);
		newButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				selectItem(NEW_JOBS);
			}
			
		});
		
		mapButton = (Button) view.findViewById(R.id.button_map);
		mapButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				selectItem(MAP);
			}
			
		});
		
		selectItem(0);
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MeggnifyActivity) activity).onFragmentAttached(FRAGMENT_POSITION);
	}
	
	void selectItem(int position) {
		Fragment fragment;
		switch(position) {
		case MY_JOBS:
			fragment = new AssignmentMyFragment();
			break;
		case NEW_JOBS:
			fragment = new AssignmentNewFragment();
			break;
		case MAP:
			fragment = new AssignmentMapFragment();
			break;
		default:
			fragment = new AssignmentMyFragment();
			break;
		}

        Bundle extras = new Bundle();
        extras.putString("token", mUserToken);
        fragment.setArguments(extras);

		getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.assignment_container, fragment)
			.commit();
	}

}
