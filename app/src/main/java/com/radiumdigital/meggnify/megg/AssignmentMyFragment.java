package com.radiumdigital.meggnify.megg;

import java.util.ArrayList;
import java.util.List;

import com.radiumdigital.meggnify.MeggnifyManager;
import com.radiumdigital.meggnify.R;
import com.radiumdigital.meggnify.model.Mission;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AssignmentMyFragment extends Fragment {

	private MeggnifyManager meggManager;

    private String mUserToken;

	private ListView missionList;
	private MissionAdapter missionAdapter;
	
	private List<Mission> missions;
	
	public AssignmentMyFragment() {
		meggManager = MeggnifyManager.sharedInstance();
		
		missions = new ArrayList<Mission>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_assignment_my, container, false);

        Bundle extras = getArguments();
        mUserToken = extras.getString("token");

		missionAdapter = new MissionAdapter(getActivity(), missions);
		
		missionList = (ListView) view.findViewById(R.id.list_mission);
		missionList.setAdapter(missionAdapter);
		missionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				selectItem(position);
			}
		});
		
		loadMission(true);
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		loadMission(false);
	}
	
	void loadMission(boolean cache) {
		new AsyncTask<Boolean, Void, Boolean>() {

			private boolean cache;
			
			@Override
			protected Boolean doInBackground(Boolean... params) {
				// TODO Auto-generated method stub
				cache = params[0];
				
				return meggManager.refreshJob(cache, mUserToken);
			}
			
			@Override
			protected void onPostExecute(Boolean status) {
				if (status) {
					List<Mission> tmpMissions = meggManager.missions; 
					missions.clear();
					for (int i = 0; i < tmpMissions.size(); i++) {
						missions.add(tmpMissions.get(i));
					}
					
					missionAdapter.notifyDataSetChanged();
				} else {
					if (!cache) {
						Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
			
		}.execute(cache);
	}
	
	void selectItem(int position) {
		Mission mission = (Mission) missionAdapter.getItem(position);
		
		Bundle extras = new Bundle();
        extras.putString("token", mUserToken);
		extras.putString("type", "my_jobs");
		extras.putParcelable("mission", mission);
		AssignmentMissionFragment fragment = new AssignmentMissionFragment();
		fragment.setArguments(extras);
		
		getActivity().getSupportFragmentManager().beginTransaction()
			.add(R.id.assignment_container, fragment)
			.addToBackStack(null)
			.commit();
	}
	
	private static class ViewHolder {
		TextView nameView;
		TextView endDateView;
		TextView endTimeView;
	}
	
	private class MissionAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater;
		private Context mContext;
		private List<Mission> mMissions;
		
		public MissionAdapter(Context context, List<Mission> missions) {
			mContext = context;
			mMissions = missions;
			mInflater = ((Activity) mContext).getLayoutInflater();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mMissions.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mMissions.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_mission_my, parent, false);
				
				holder = new ViewHolder();
				holder.nameView = (TextView) convertView.findViewById(R.id.mission_name);
				holder.endDateView = (TextView) convertView.findViewById(R.id.mission_end_date);
				holder.endTimeView = (TextView) convertView.findViewById(R.id.mission_end_time);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			Mission mission = (Mission) getItem(position);
			if (mission != null) {
				holder.nameView.setText(mission.name);
				holder.endDateView.setText(mission.endDate);
				holder.endTimeView.setText(mission.endTime);
			}
			
			return convertView;
		}
	}

}
