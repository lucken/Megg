package com.radiumdigital.meggnify.mission;

import java.util.ArrayList;
import java.util.List;

import com.radiumdigital.meggnify.MeggnifyManager;
import com.radiumdigital.meggnify.R;
import com.radiumdigital.meggnify.R.id;
import com.radiumdigital.meggnify.R.layout;
import com.radiumdigital.meggnify.model.Mission;
import com.radiumdigital.meggnify.model.Task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MissionFragment extends Fragment {

	private MissionTaskCallback mCallback;
	
	private MeggnifyManager meggManager;

    private String mUserToken;
    private Mission mission;

	private List<Task> tasks;
	private TaskAdapter taskAdapter;
	private ListView taskList;
	
	private Button submitButton;
	
	public MissionFragment() {
		meggManager = MeggnifyManager.sharedInstance();
		
		tasks = new ArrayList<Task>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mission, container, false);
		
		Bundle extras = getArguments();
        mUserToken = extras.getString("token");
		mission = extras.getParcelable("mission");

		taskAdapter = new TaskAdapter(getActivity(), tasks);
		taskList = (ListView) view.findViewById(R.id.list_task);
		taskList.setAdapter(taskAdapter);
		taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				selectItem(position);
			}
			
		});
		
		submitButton = (Button) view.findViewById(R.id.submit_mission);
		submitButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mCallback.onSubmitMission();
			}
			
		});
		
		loadTask();
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (MissionTaskCallback) activity;
		} catch(ClassCastException e) {
			throw new ClassCastException("Unimplemented");
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mCallback = null;
	}
	
	void selectItem(int position) {
		Task task = (Task) taskAdapter.getItem(position);
		Bundle extras = new Bundle();
		extras.putParcelable("task", task);
		
		TaskFragment fragment = new TaskFragment();
		fragment.setArguments(extras);
		
		getActivity().getSupportFragmentManager().beginTransaction()
			.add(R.id.container, fragment)
			.addToBackStack(null)
			.commit();
	}
	
	void loadTask() {
		new AsyncTask<Integer, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				int missionId = params[0];
				
				return meggManager.refreshTask(missionId, mUserToken);
			}
			
			@Override
			protected void onPostExecute(Boolean status) {
				if (status) {
					List<Task> tmpTasks = meggManager.tasks;
					tasks.clear();
					for (int i = 0; i < tmpTasks.size(); i++) {
						tasks.add(tmpTasks.get(i));
					}
					
					taskAdapter.notifyDataSetChanged();
				} else {
					Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
				}
			}
			
		}.execute(mission.missionId);
	}
	
	public static interface MissionTaskCallback {
		void onSubmitMission();
	}
	
	private static class ViewHolder {
		TextView titleView;
	}
	
	private class TaskAdapter extends BaseAdapter {

		private Context mContext;
		private List<Task> mTasks;
		private LayoutInflater mInflater;
		
		public TaskAdapter(Context context, List<Task> tasks) {
			mContext = context;
			mTasks = tasks;
			mInflater = ((Activity) mContext).getLayoutInflater();
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTasks.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mTasks.get(position);
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
				convertView = mInflater.inflate(R.layout.item_task, parent, false);
				
				holder = new ViewHolder();
				holder.titleView = (TextView) convertView.findViewById(R.id.task_title);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			Task task = (Task) getItem(position);
			if (task != null) {
				holder.titleView.setText(task.title);
			}
			
			return convertView;
		}
	}

}
