package com.meggnify.mission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.meggnify.MeggnifyManager;
import com.meggnify.R;
import com.meggnify.helper.Constants;
import com.meggnify.lib.LocalContext;
import com.meggnify.model.Mission;
import com.meggnify.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MysteryAuditActivity extends ActionBarActivity implements IAuditListener {

    private Context mContext;

    private MeggnifyManager meggManager;

    private String mUserToken;

    private Mission mission;

    private TaskAdapter taskAdapter;
    private List<Task> tasks;
    private ListView taskList;

    private TextView instructionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystery_audit);

        mContext = LocalContext.getContext();

        meggManager = MeggnifyManager.sharedInstance();

        mUserToken = getToken(mContext);

        Bundle extras = getIntent().getExtras();
        mission = extras.getParcelable("mission");

        setTitle(mission.name);

        tasks = new ArrayList<Task>();
        taskAdapter = new TaskAdapter(this, tasks);

        taskList = (ListView) findViewById(R.id.list_task);
        taskList.setAdapter(taskAdapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                selectItem(position);
            }
        });

        instructionView = (TextView) findViewById(R.id.mission_instruction);
        instructionView.setText(mission.instruction);

        loadMission(mission.missionId, false);
    }

    @Override
    public void onBackPressed() {

    }

    private String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token  = prefs.getString(Constants.PREF_USER_TOKEN, "");
        if (token.isEmpty()) {
            return null;
        }
        return token;
    }

    void loadMission(int missionId, final boolean cache) {
        new AsyncTask<Integer, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Integer... params) {
                // TODO Auto-generated method stub
                int missionId = params[0];

                return meggManager.refreshMission(missionId);
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
                    if (!cache) {
                        //Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }.execute(missionId);
    }

    void selectItem(int position) {
        Task task = (Task) tasks.get(position);
        if (task.answer_type.equals("Photo")) {
            Intent taskIntent = new Intent(this, PhotoActivity.class);
            taskIntent.putExtra("token", mUserToken);
            taskIntent.putExtra("mission", mission);
            taskIntent.putExtra("task", task);
            startActivity(taskIntent);
        } else if (task.answer_type.equals("Video")) {
            Intent taskIntent = new Intent(this, VideoActivity.class);
            taskIntent.putExtra("token", mUserToken);
            taskIntent.putExtra("mission", mission);
            taskIntent.putExtra("task", task);
            startActivity(taskIntent);
        } else if (task.answer_type.equals("Audio")) {
            Intent taskIntent = new Intent(this, AudioActivity.class);
            taskIntent.putExtra("token", mUserToken);
            taskIntent.putExtra("mission", mission);
            taskIntent.putExtra("task", task);
            startActivity(taskIntent);
        } else if (task.answer_type.equals("answer")) {
            Intent taskIntent = new Intent(this, ChoiceActivity.class);
            taskIntent.putExtra("token", mUserToken);
            taskIntent.putExtra("mission", mission);
            taskIntent.putExtra("task", task);
            startActivity(taskIntent);
        } else {
            Intent taskIntent = new Intent(this, TaskActivity.class);
            taskIntent.putExtra("token", mUserToken);
            taskIntent.putExtra("mission", mission);
            taskIntent.putExtra("task", task);
            startActivity(taskIntent);
        }
    }

    private static class ViewHolder {
        TextView questionView;
        ImageView iconView;
    }

    private class TaskAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context mContext;
        private List<Task> mTasks;

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
                holder.questionView = (TextView) convertView.findViewById(R.id.task_question);
                holder.iconView = (ImageView) convertView.findViewById(R.id.task_icon);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Task task = (Task) getItem(position);
            if (task != null) {
                holder.questionView.setText(task.question);
                holder.iconView.setImageResource(task.icon);
            }

            return convertView;
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mystery_audit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
